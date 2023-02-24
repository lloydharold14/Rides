package com.tkh.rides.presentation.vehiclesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tkh.rides.R
import com.tkh.rides.databinding.FragmentVehicleListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VehicleListFragment : Fragment(R.layout.fragment_vehicle_list) {

    private lateinit var binding: FragmentVehicleListBinding
    private val viewModel: VehiclesListViewModel by viewModels()
    private lateinit var adapter: VehiclesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVehicleListBinding.bind(view)
        binding.progressBar.visibility = View.INVISIBLE
        setupSwipeToRefresh()
        setupRecyclerView()
        setupObserver()
        setupMessageObserver()
        getVehicleListSize()
        viewVehiclesList()
        viewVehicleDetails()

    }

    private fun setupSwipeToRefresh() {
        if (viewModel.uiState.value.vehicles.isNotEmpty()) {
            binding.swipeRefreshList.setOnRefreshListener {
                viewModel.getVehicles()
            }
        } else {
            binding.swipeRefreshList.isEnabled = false
        }

    }

    private fun getVehicleListSize() {
        binding.etSearch.setText(viewModel.uiState.value.size.toString())
        binding.etSearch.addTextChangedListener { editable ->
            editable?.let {
                if (editable.toString().isNotEmpty()) {

                    try {
                        val size = Integer.parseInt(editable.toString())
                        viewModel.onEvent(ListEvent.OnSizeChange(size))
                    } catch (e: java.lang.NumberFormatException) { // handle your exception

                    }
                }
            }
        }
    }

    private fun viewVehicleDetails() = adapter.setOnItemClickListener {
        findNavController().navigate(
            VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(
                it
            )
        )
    }


    private fun viewVehiclesList() {
        binding.buttonSearch.setOnClickListener {
            binding.rvSearchVehicles.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.onEvent(ListEvent.OnSearch)
        }
    }

    private fun setupMessageObserver() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.eventFlow.collect { event ->
                when (event) {
                    is ListEvent.onError -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupObserver() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.uiState.collect {
                when {
                    it.vehicles.isNotEmpty() -> {
                        adapter.differ.submitList(it.vehicles)
                        binding.progressBar.visibility = View.GONE
                        binding.rvSearchVehicles.visibility = View.VISIBLE
                        binding.swipeRefreshList.isRefreshing = false
                        binding.swipeRefreshList.isEnabled = true
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = VehiclesAdapter()
        binding.rvSearchVehicles.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(activity)
        }

        binding.rvSearchVehicles.addItemDecoration(
            DividerItemDecoration(
                binding.rvSearchVehicles.context,
                (binding.rvSearchVehicles.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvSearchVehicles.adapter = adapter
    }

}
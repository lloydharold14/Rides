package com.tkh.rides.presentation.vehiclesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        setupRecyclerView()
        setupObserver()
        getVehicleListSize()
        viewVehiclesList()

    }

    private fun getVehicleListSize() {
        binding.etSearch.setText(viewModel.size.value.toString())
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

    private fun viewVehicleDetails() {
        adapter.setOnItemClickListener {
            //TODO IMPLEMENT ONITEM CLICKED
        }
    }

    private fun viewVehiclesList() {
        binding.buttonSearch.setOnClickListener {
            viewModel.onEvent(ListEvent.OnSearch)
        }
    }
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            adapter.differ.submitList(it.data)
                            binding.rvSearchVehicles.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.rvSearchVehicles.visibility = View.GONE
                        }
                        is UiState.Error -> {

                        }
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
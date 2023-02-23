package com.tkh.rides.presentation.estimatedCarbon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tkh.rides.R
import com.tkh.rides.databinding.FragmentEstimatedCarbonBinding

class EstimatedCarbonFragment : BottomSheetDialogFragment(R.layout.fragment_estimated_carbon) {

    private val viewModel: EstimatedCarbonViewModel by viewModels()
    private lateinit var binding: FragmentEstimatedCarbonBinding
    private val navigationArgs: EstimatedCarbonFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEstimatedCarbonBinding.bind(view)

        var estimatedCarbon = viewModel.getEstimatedCarbon(navigationArgs.kilometrage)

        binding.tvCarbon.text = "The estimated carbon emission is $estimatedCarbon"
    }


}
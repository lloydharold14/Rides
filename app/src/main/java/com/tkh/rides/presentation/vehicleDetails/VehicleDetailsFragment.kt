package com.tkh.rides.presentation.vehicleDetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.tkh.rides.R
import com.tkh.rides.databinding.FragmentVehicleDetailsBinding
import com.tkh.rides.domain.model.Vehicle


class VehicleDetailsFragment : Fragment(R.layout.fragment_vehicle_details) {
    private lateinit var binding: FragmentVehicleDetailsBinding
    private val navigationArgs: VehicleDetailsFragmentArgs by navArgs()
    private lateinit var mVehicle: Vehicle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVehicleDetailsBinding.bind(view)
        showVehicleDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun showVehicleDetails() {
        mVehicle = navigationArgs.vehicle
        binding.tvVin.text = "Vin: ${mVehicle.vin}"
        binding.tvMakeModel.text = "Make: ${mVehicle.make_and_model}"
        binding.tvColor.text = "color: ${mVehicle.color}"
        binding.tvCarType.text = "Car Type: ${mVehicle.car_type}"
    }
}
package com.tkh.rides.presentation.estimatedCarbon

import androidx.lifecycle.ViewModel
import com.tkh.rides.domain.use_case.CalculateCarbonEmission


class EstimatedCarbonViewModel() : ViewModel() {

    fun getEstimatedCarbon(kilometrage: Int): Int = CalculateCarbonEmission().invoke(kilometrage)
}
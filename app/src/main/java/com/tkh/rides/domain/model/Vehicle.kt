package com.tkh.rides.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vehicle(
    val car_type: String,
    val color: String,
    val id: Int,
    val kilometrage: Int,
    val make_and_model: String,
    val vin: String
) : Parcelable
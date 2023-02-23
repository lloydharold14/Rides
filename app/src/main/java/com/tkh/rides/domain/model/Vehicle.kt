package com.tkh.rides.domain.model

data class Vehicle(
    val car_type: String,
    val color: String,
    val id: Int,
    val kilometrage: Int,
    val make_and_model: String,
    val vin: String
)
package com.tkh.rides.domain.repository

import com.tkh.rides.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface RidesRepository {

    suspend fun getVehicleList(size: Int): Flow<List<Vehicle>>
}
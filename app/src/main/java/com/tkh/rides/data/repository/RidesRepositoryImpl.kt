package com.tkh.rides.data.repository

import android.util.Log
import com.tkh.rides.data.remote.RidesApi
import com.tkh.rides.data.remote.dto.toVehicle
import com.tkh.rides.domain.repository.RidesRepository
import kotlinx.coroutines.flow.flow

class RidesRepositoryImpl(
    private val api: RidesApi
) : RidesRepository {
    override suspend fun getVehicleList(size: Int) = flow {
        try {
            emit(api.vehiclesList(size = size.toString())
                .sortedBy { it.vin }
                .map { it.toVehicle() })
        } catch (e : java.lang.Exception){
            Log.d("tkhtech", "getVehicleList: $e")
        }

    }
}
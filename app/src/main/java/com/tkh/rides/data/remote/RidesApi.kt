package com.tkh.rides.data.remote

import com.tkh.rides.data.remote.dto.VehiclesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RidesApi {
    @GET("vehicle/random_vehicle?")
    suspend fun vehiclesList(
        @Query("size") size: String
    ): List<VehiclesDto>

}
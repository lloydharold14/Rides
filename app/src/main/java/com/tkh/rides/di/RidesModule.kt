package com.tkh.rides.di

import com.tkh.rides.data.remote.RidesApi
import com.tkh.rides.data.repository.RidesRepositoryImpl
import com.tkh.rides.domain.repository.RidesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RidesModule {

    @Provides
    @Singleton
    fun provideApi(): RidesApi {
        return Retrofit.Builder()
            .baseUrl("https://random-data-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RidesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: RidesApi): RidesRepository {
        return RidesRepositoryImpl(
            api = api
        )
    }


}
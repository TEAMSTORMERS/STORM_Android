package com.stormers.storm.network

import com.stormers.storm.SecretStrings
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = SecretStrings.BASE_URL

    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(service: Class<T>) : T {
        return getInstance().create(service)
    }
}
package com.stormers.storm.network

import com.stormers.storm.ui.LoginActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    //Todo: URL 채우기

    private const val BASE_URL = ""

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
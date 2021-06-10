package com.stormers.storm.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.request(callback: RequestCallback<T>) {
    enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                callback.onSuccess(response.body()!!)
                response.errorBody()
            } else {
                callback.onFailure(false, response.errorBody())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onFailure(true)
        }
    })
}

interface RequestCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(isNetworkError: Boolean, errorBody: ResponseBody? = null)
}
package com.stormers.storm.network

data class Response (
    val status: Int,
    val success: Boolean,
    val message: String
)
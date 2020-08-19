package com.stormers.storm.UserWithdrawal.model

import com.google.gson.annotations.SerializedName

data class UserWithdrawalModel(
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("user_password")
    val userPassword : String,
    val reason : String?
)
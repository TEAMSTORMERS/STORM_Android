package com.stormers.storm.mypage.model

import com.google.gson.annotations.SerializedName

data class MyPageModel(
    @SerializedName("user_img")
    val userImage : String,
    @SerializedName("user_name")
    val userName : String,
    @SerializedName("user_img_flag")
    val userImageFlag : Int
)
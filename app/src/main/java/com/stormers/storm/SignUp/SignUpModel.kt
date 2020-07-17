package com.stormers.storm.SignUp

import com.google.gson.annotations.SerializedName
import java.io.File

data class SignUpModel(
    @SerializedName("user_name")
    val userName : String,
    @SerializedName("user_token_google")
    val userTokenGoogle : String?,
    @SerializedName("user_token_kakao")
    val userTokenKakao : String?,
    @SerializedName("user_img")
    val userImage : File
)
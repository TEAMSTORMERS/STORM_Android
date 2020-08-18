package com.stormers.storm.SignUp

import com.google.gson.annotations.SerializedName
import java.io.File

data class SignUpModel(
    @SerializedName("user_img")
    val userImage : File,
    @SerializedName("user_name")
    val userName : String,
    @SerializedName("user_email")
    val userEmail : String,
    @SerializedName("user_password")
    val userPassword : String,
    @SerializedName("user_img_flag")
    val USER_IMG_FLAG : Int
)
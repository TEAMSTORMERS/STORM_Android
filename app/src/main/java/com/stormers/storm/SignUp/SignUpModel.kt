package com.stormers.storm.SignUp

import com.google.gson.annotations.SerializedName
import java.io.File

data class CheckEmailDuplicationModel(
    @SerializedName("user_email")
    val userEmail : String
)
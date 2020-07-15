package com.stormers.storm.project.model

import com.stormers.storm.user.UserModel
import java.util.function.BinaryOperator

data class ResponseProjectUserListModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<UserModel>
)
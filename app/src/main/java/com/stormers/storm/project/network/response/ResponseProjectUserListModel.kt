package com.stormers.storm.project.network.response

import com.stormers.storm.user.UserModel

data class ResponseProjectUserListModel(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<UserModel>
)
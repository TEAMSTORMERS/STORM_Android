package com.stormers.storm.project.network.response

import com.stormers.storm.user.User

data class ResponseParticipant(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<User>
)
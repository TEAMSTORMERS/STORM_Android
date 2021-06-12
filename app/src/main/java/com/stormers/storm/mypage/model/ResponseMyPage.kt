package com.stormers.storm.mypage.model

data class ResponseMyPage(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MyPageModel
)
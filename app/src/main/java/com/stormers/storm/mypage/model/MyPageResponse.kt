package com.stormers.storm.mypage.model

data class MyPageResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MyPageModel
)
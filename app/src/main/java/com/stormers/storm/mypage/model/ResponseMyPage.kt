package com.stormers.storm.mypage.model

import com.stormers.storm.mypage.model.MyPageModel

data class ResponseMyPage(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MyPageModel
)
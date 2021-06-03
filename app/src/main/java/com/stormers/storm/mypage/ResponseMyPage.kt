package com.stormers.storm.mypage

import com.stormers.storm.mypage.entity.MypageData

data class ResponseMyPage(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MypageData
)
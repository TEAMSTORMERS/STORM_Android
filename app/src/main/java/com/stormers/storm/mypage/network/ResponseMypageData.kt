package com.stormers.storm.mypage.network

@Deprecated("Use ResponseMyPage")
data class ResponseMypageData (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : MypageData
)
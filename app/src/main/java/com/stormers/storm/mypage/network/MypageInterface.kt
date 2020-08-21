package com.stormers.storm.mypage.network

import com.stormers.storm.network.SimpleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MypageInterface {

    @GET("/user/mypage/{user_idx}")
    fun responseMypageData(
        @Path("user_idx") user_idx : Int) : Call<ResponseMypageData>

    @FormUrlEncoded
    @PUT("/user/mypage/name")
    fun updateMypageName(
        @Field("user_idx") user_idx : Int,
        @Field("user_name") user_name : String) : Call<SimpleResponse>

    @Multipart
    @PUT("/user/mypage/img")
    fun updateMypageImage(
        @Part("user_idx") user_idx : RequestBody,
        @Part user_img : MultipartBody.Part,
        @Part("user_img_flag") user_img_flag : RequestBody) : Call<SimpleResponse>
}
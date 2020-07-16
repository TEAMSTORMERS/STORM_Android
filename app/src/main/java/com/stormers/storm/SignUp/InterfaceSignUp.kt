package com.stormers.storm.SignUp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface InterfaceSignUp{
    @Multipart
    @POST("/user")
    fun interfaceSignUp(@Part("user_name")userName : RequestBody, @Part("user_token_google")userTokenGoogle : RequestBody?,
                        @Part("user_token_kakao")userTokenKakao : RequestBody?, @Part("user_img")userImage : MultipartBody.Part?) : Call<ResponseSignUpModel>
}
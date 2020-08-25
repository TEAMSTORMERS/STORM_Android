package com.stormers.storm.SignUp

import com.stormers.storm.network.SimpleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface InterfaceSignUp{
    @Multipart
    @POST("/user/signup")
    fun interfaceSignUp(
        @Part user_img : MultipartBody.Part, @Part("user_name") userName: RequestBody,
        @Part("user_email") userEmail: RequestBody, @Part("user_password") userPassword: RequestBody,
        @Part("user_img_flag") UserImageFlag: RequestBody
    ) : Call<ResponseSignUpModel>


    @POST("/user/checkemail")
    fun requestCheckEmailDuplication(@Body body : CheckEmailDuplicationModel) : Call<SimpleResponse>
}
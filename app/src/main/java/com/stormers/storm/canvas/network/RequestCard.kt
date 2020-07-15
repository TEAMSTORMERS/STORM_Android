package com.stormers.storm.canvas.network

import com.stormers.storm.card.model.RequestCardModel
import com.stormers.storm.network.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RequestCard {

    @Multipart
    @POST("/card")
    fun postCard(@Part("user_idx")userIdx: RequestBody, @Part("project_idx")projectIdx: RequestBody,
                 @Part("round_idx")roundIdx: RequestBody, @Part card_img: MultipartBody.Part?, @Part("card_txt") card_txt: RequestBody?) : Call<Response>
}
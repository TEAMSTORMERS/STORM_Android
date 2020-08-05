package com.stormers.storm.ui

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.adapter.ProjectUserImageAdapter
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.round.adapter.RoundUserImageAdapter
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseRoundUserImageModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.item_round_part_detail.*
import kotlinx.android.synthetic.main.layout_list_user_profile.*
import retrofit2.Callback
import retrofit2.Response

class ItemRoundPartDetailActivity : BaseActivity() {

    private var projectIdx = -1

    private lateinit var retrofitClient_roundInfo: RequestRound
    private lateinit var retrofitClient: RequestProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_round_part_detail)

        projectIdx = intent.getIntExtra("projectIdx", -1)

        retrofitClient = RetrofitClient.create(RequestProject::class.java)
        retrofitClient_roundInfo = RetrofitClient.create(RequestRound::class.java)

        val roundUserImageAdapter = RoundUserImageAdapter()
        recyclerview_user_profile.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL,false)
        recyclerview_user_profile.addItemDecoration(MarginDecoration(baseContext, 9, RecyclerView.HORIZONTAL))
        recyclerview_user_profile.adapter = roundUserImageAdapter


        retrofitClient_roundInfo.getRoundUserImage(projectIdx).enqueue(object :Callback<ResponseRoundUserImageModel>{
            override fun onFailure(call: retrofit2.Call<ResponseRoundUserImageModel>, t: Throwable) {
                Log.d("라운드 유저 이미지 통신실패","${t}")
            }

            override fun onResponse(
                call: retrofit2.Call<ResponseRoundUserImageModel>,
                response: Response<ResponseRoundUserImageModel>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        roundUserImageAdapter.addAll(response.body()!!.data)
                    } else {
                        Log.d("roundUserImage", "${response.body()!!.message}, ${response.errorBody()}")
                    }
                }
            }
        })

    }
}
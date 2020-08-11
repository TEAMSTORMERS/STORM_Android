package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.BaseResponse

import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.network.RequestRound
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  RoundSettingActivity : BaseActivity() {

    private var projectIdx = preference.getProjectIdx()
    private var userIdx = preference.getUserIdx()

    private lateinit var retrofitClient: RequestRound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        projectIdx = preference.getProjectIdx()!!
        userIdx = preference.getUserIdx()!!

        preference.setHost(true)

        stormtoolbar_roundsetting.setExitButton(View.OnClickListener {
            //Todo: 프로젝트 나가기
        })


        textview_projectcard_title.text = preference.getProjectName()

        retrofitClient = RetrofitClient.create(RequestRound::class.java)

        goToFragment(HostRoundSettingFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }
}
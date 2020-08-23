package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.HostRoundSettingActivity
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HostRoundWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_start) {

    private lateinit var activityButton: StormButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //첫 번째 라운드가 아니라면 라운드가 시작됨을 소켓으로 알림
        if (!isFirstRound) {
            startRoundAgain()
        }

        //라운드 정보 초기화
        initRoundInfo(GlobalApplication.currentRound!!.roundPurpose!!, GlobalApplication.currentRound!!.roundTime!!)

        //확인 버튼 초기화
        initActivityButton()
    }

    private fun startRoundAgain() {
        SocketClient.sendEvent(SocketClient.NEXT_ROUND, GlobalApplication.currentProject!!.projectCode!!)
        Log.d(TAG, "[socket] nextRound: ${GlobalApplication.currentProject!!.projectCode!!}")
    }

    private fun initActivityButton() {

        activityButton = (activity as HostRoundSettingActivity).stormButton_ok_host_round_setting

        activityButton.setOnClickListener {

            if (isFirstRound) {
                requestStartProject()
            }

            startRoundSocket()

            startRound()
        }
    }

    private fun startRoundSocket() {
        SocketClient.sendEvent(SocketClient.ROUND_START_HOST, GlobalApplication.currentProject!!.projectCode!!)
        Log.d(TAG, "[socket] roundStartHost: ${GlobalApplication.currentProject!!.projectCode!!}")
    }

    private fun requestStartProject() {
        RetrofitClient.create(RequestProject::class.java).projectStart(GlobalApplication.currentProject!!.projectIdx)
            .enqueue(object : Callback<SimpleResponse> {
                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    Log.d(TAG, "requestStartProject: Fail. ${t.message}")
                }

                override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "requestStartProject: Success.")
                        } else {
                            Log.d(TAG, "requestStartProject: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "requestStartProject: Not successful, ${response.message()}")
                    }
                }
            })
    }
}
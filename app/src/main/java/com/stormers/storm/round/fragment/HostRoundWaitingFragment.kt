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
import com.stormers.storm.ui.RoundSettingActivity
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_hostwaiting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder


class HostRoundWaitingFragment : BaseWaitingFragment(R.layout.fragment_hostwaiting) {

    private lateinit var activityButton: StormButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //첫 번째 라운드가 아니라면 라운드가 시작됨을 소켓으로 알림
        if (!isFirstRound) {
            startRoundAgain()
        }

        //라운드 정보 초기화
        GlobalApplication.currentRound?.let {
            initRoundInfo(it.roundPurpose!!, it.roundTime!!, it.roundNumber!!)
        }

        //확인 버튼 초기화
        initActivityButton()
    }

    private fun initRoundInfo(roundPurpose: String, roundTime: Int, roundNumber: Int) {
        textview_hostwaiting_roundpurpose.text = StringBuilder("총 ").append(roundTime).append("분 예정").toString()
        textview_hostwaiting_roundtime.text = roundPurpose
        textview_hostwaiting_roundnumber.text = StringBuilder("Round ").append(roundNumber).append(" 설정 완료").toString()
    }

    private fun startRoundAgain() {
        SocketClient.sendEvent(SocketClient.NEXT_ROUND, GlobalApplication.currentProject!!.projectCode!!)
        Log.d(TAG, "[socket] nextRound: ${GlobalApplication.currentProject!!.projectCode!!}")
    }

    private fun initActivityButton() {

        activityButton = (activity as RoundSettingActivity).stormButton_ok_host_round_setting

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
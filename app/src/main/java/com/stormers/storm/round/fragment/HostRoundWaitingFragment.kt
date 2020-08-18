package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundSettingActivity
import com.stormers.storm.user.UserModel
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*


class HostRoundWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_start) {

    private lateinit var activityButton: StormButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //라운드 정보 초기화
        initRoundInfo(GlobalApplication.currentRound!!.roundPurpose!!, GlobalApplication.currentRound!!.roundTime!!)

        //확인 버튼 초기화
        initActivityButton()
    }

    private fun initActivityButton() {

        activityButton = (activity as RoundSettingActivity).stormButton_ok_host_round_setting

        activityButton.setOnClickListener {

            startRoundSocket()

            startRound()
        }
    }

    private fun startRoundSocket() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("roundStartHost", GlobalApplication.currentProject!!.projectCode!!)
    }
}
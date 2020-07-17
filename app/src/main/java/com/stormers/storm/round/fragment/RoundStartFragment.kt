package com.stormers.storm.round.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.ui.RoundSettingActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*


class RoundStartFragment : BaseWaitingFragment(R.layout.fragment_round_start) {

    private lateinit var activityButton: StormButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //라운드 정보 받아오기
        getRoundInfo()

        showRoundUserLIst(preference.getRoundIdx()!!)

        refreshParticipantSocket()
    }

    override fun afterGettingRoundInfo(roundIdx: Int) {
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

        SocketClient.sendEvent("roundStartHost", preference.getProjectCode()!!)
    }

    private fun refreshParticipantSocket() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.responseEvent("roundComplete", Emitter.Listener {
            Log.d("refresh_socket", "참가자가 들어왔습니다.")
            showRoundUserLIst(preference.getRoundIdx()!!)
        })
    }

}
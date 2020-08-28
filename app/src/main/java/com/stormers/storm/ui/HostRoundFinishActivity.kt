package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.base.BaseRoundFinishActivity
import com.stormers.storm.ui.GlobalApplication.Companion.currentRound
import java.lang.StringBuilder

class HostRoundFinishActivity : BaseRoundFinishActivity() {

    private var buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDialogButton()

        initDialog()

        finishButton.run {
            visibility = View.VISIBLE

            setOnClickListener {
                dialog.show(supportFragmentManager, "round_finish")
            }
        }
    }

    private fun initDialogButton() {

        if(currentRound!!.roundNumber == 9) {
            buttonArray.add(
                StormDialogButton("프로젝트 종료", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        finishProject()
                    }
                })
            )
        } else {
            buttonArray.add(
                StormDialogButton("다음 ROUND 진행", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        startNextRound()
                    }
                })
            )
            buttonArray.add(
                StormDialogButton("프로젝트 종료 후 최종 정리", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        finishProject()
                    }
                })
            )
        }
    }

    private fun startNextRound() {
        SocketClient.sendEvent(SocketClient.PREPARE_NEXT_ROUND, GlobalApplication.currentProject!!.projectCode!!)
        Log.d(TAG, "[socket] prepareNextRound: ${GlobalApplication.currentProject!!.projectCode!!}")

        startActivity(Intent(this@HostRoundFinishActivity, HostRoundSettingActivity::class.java))
        finish()
    }


    private fun initDialog() {
        val round = StringBuilder()
        round.append("ROUND ")
            .append(GlobalApplication.currentRound!!.roundNumber)
            .append(" 종료")

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, round.toString())
            .setButtonArray(buttonArray)
            .build()
    }
}
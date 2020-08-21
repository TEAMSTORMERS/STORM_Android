package com.stormers.storm.round.base

import android.content.Intent
import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.SocketClient
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.ParticipatedProjectDetailActivity
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.lang.StringBuilder

open class BaseRoundProgressActivity : BaseActivity() {

    protected val roundPurpose = GlobalApplication.currentRound!!.roundPurpose!!
    protected val roundNumber = GlobalApplication.currentRound!!.roundNumber!!
    protected val roundTime = GlobalApplication.currentRound!!.roundTime!!
    protected val projectName = GlobalApplication.currentProject!!.projectName!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        //상단 라운드 정보 보여주기
        initRoundInfo()
    }

    private fun initRoundInfo() {
        val roundNumber = StringBuilder()

        roundNumber.append("ROUND ")
            .append(this.roundNumber).toString()

        textView_round_goal.text = roundPurpose
        textView_round.text = roundNumber

        textView_project_name.text = GlobalApplication.currentProject!!.projectName
    }

    protected fun startDetailActivity () {
        val intent = Intent(this, ParticipatedProjectDetailActivity::class.java)
        intent.putExtra("projectIdx", GlobalApplication.currentProject!!.projectIdx)
        intent.putExtra("isAfterProject", true)

        SocketClient.disconnectionAndClose()

        startActivity(intent)
        finish()
    }
}
package com.stormers.storm.round.base

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.lang.StringBuilder

open class BaseRoundProgressActivity : OnProjectActivity() {

    protected val roundPurpose = GlobalApplication.currentRound!!.roundPurpose!!
    protected val roundNumber = GlobalApplication.currentRound!!.roundNumber!!
    protected val roundTime = GlobalApplication.currentRound!!.roundTime!!
    protected val projectName = GlobalApplication.currentProject!!.projectName!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        setStormToolbar(stormtoolbar_roundprogress)

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
}
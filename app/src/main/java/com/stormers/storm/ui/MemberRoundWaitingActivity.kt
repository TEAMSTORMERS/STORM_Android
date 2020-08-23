package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.MemberWaitingFragment

class MemberRoundWaitingActivity : BaseRoundWaitingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstRound = intent.getBooleanExtra("isFirstRound", true)

        goToFragment(MemberWaitingFragment::class.java, Bundle().apply {
            putBoolean("isFirstRound", isFirstRound)
        })

    }
}
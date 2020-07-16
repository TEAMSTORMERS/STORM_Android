package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*

class RoundSettingActivity : BaseActivity() {

    var projectIdx = -1

    var roundIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        projectIdx = intent.getIntExtra("projectIdx", -1)
        roundIdx = intent.getIntExtra("roundIdx", -1)

        goToFragment(HostRoundSettingFragment::class.java, Bundle().apply {
            putBoolean("newRound", true)
            putInt("projectIdx", projectIdx)
            putInt("roundIdx", roundIdx)
        })
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

}
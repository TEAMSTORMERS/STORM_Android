package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*

class RoundSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        goToFragment(HostRoundSettingFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

}
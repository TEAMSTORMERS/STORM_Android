package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment

class HostRoundSettingActivity : BaseRoundWaitingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goToFragment(HostRoundSettingFragment::class.java, null)
    }
}
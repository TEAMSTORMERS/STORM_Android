package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.HostRoundWaitingFragment

class HostRoundSettingActivity : BaseRoundWaitingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isPromotion = intent.getBooleanExtra("isPromotion", false)

        if (!isPromotion) {
            goToFragment(HostRoundSettingFragment::class.java, null)
        } else {
            goToFragment(HostRoundWaitingFragment::class.java, Bundle().apply {
                putBoolean("isPromotion", isPromotion)
            })
        }
    }
}
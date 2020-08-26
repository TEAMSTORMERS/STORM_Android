package com.stormers.storm.ui

import android.os.Bundle
import android.text.Layout
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.HostRoundWaitingFragment
import com.stormers.storm.util.KeyBoardVisibilityUtils
import kotlinx.android.synthetic.main.activity_round_setting.*

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

        scrollViewKeyBoard(scrollview_round_setting)

    }
}
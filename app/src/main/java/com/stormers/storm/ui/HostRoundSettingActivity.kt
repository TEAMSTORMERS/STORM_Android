package com.stormers.storm.ui

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.HostRoundWaitingFragment
import com.stormers.storm.util.KeyBoardVisibilityUtils
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*

class HostRoundSettingActivity : BaseRoundWaitingActivity() {

    private lateinit var keyboardVisibilityUtils: KeyBoardVisibilityUtils

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
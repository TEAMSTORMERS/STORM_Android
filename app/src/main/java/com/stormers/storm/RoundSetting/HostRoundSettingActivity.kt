package com.stormers.storm.RoundSetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import kotlinx.android.synthetic.main.activity_host_round_setting.*

class HostRoundSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        viewpager_host_round_setting_fragment.adapter = RoundSettingAdapter(supportFragmentManager)
        viewpager_host_round_setting_fragment.offscreenPageLimit = 2

        }

}
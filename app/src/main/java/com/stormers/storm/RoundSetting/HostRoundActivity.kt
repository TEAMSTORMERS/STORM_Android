package com.stormers.storm.RoundSetting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stormers.storm.R
import kotlinx.android.synthetic.main.activity_host_round_setting.*

class HostRoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        viewpager_host_round_setting_fragment.adapter = RoundAdapter(supportFragmentManager)
        viewpager_host_round_setting_fragment.offscreenPageLimit = 2

    }

}
package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.round.fragment.RoundStartFragment

class RoundStartActivity : BaseActivity() {
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        goToFragment(RoundStartFragment::class.java, null)

        }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

}
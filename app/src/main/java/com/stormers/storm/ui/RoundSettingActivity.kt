package com.stormers.storm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*

class RoundSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

       // addFragment(HostRoundSettingFragment())
        goToFragment(HostRoundSettingFragment::class.java,null)

    }

    private fun addFragment(fragment: Fragment) {
        // val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        // transaction.replace(R.id.constraint_host_round, fragment).commitAllowingStateLoss()
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }
}
package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*

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
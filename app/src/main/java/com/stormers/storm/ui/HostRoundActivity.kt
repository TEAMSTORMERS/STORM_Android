package com.stormers.storm.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.round.adapter.RoundPagerAdapter
import com.stormers.storm.round.fragment.RoundSettingWaitingMemberFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*

class HostRoundActivity : AppCompatActivity() {
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)
        ShowTabWaitingForStartingProject()


        stormButton_nonstart_host_round_setting.setOnClickListener {
            if (imageview_checkrules_checkcircle.visibility == View.VISIBLE) {
                stormButton_nonstart_host_round_setting.visibility = View.INVISIBLE
                ShowTabRoundSettingWaitingMember()
            }
        }
    }


    fun ShowTabWaitingForStartingProject() {
        val transaction = manager.beginTransaction()
        val fragment = WaitingForStartingProjectFragment()
        transaction.replace(R.id.constraint_host_round, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun ShowTabRoundSettingWaitingMember() {
        val transaction = manager.beginTransaction()
        val fragment = RoundSettingWaitingMemberFragment()
        transaction.replace(R.id.constraint_host_round, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
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
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.round.adapter.RoundPagerAdapter
import com.stormers.storm.round.fragment.RoundSettingWaitingMemberFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*

class HostRoundActivity : BaseActivity() {
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)
        goToFragment(WaitingForStartingProjectFragment::class.java, null)

        stormButton_start_host_round_setting.setActivation(false)

        stormButton_start_host_round_setting.setOnClickListener {
            if (imageview_checkrules_checkcircle.visibility == View.VISIBLE) {
                stormButton_start_host_round_setting.visibility = View.INVISIBLE
                goToFragment(RoundSettingWaitingMemberFragment::class.java, null)
            }
        }
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

}
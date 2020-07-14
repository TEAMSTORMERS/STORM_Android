package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*

class RoundSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        goToFragment(HostRoundSettingFragment::class.java, null)

        //stormButton_start_host_round_setting.setActivation(false)

        stormButton_start_host_round_setting.setOnClickListener {
            //if (imageview_checkrules_checkcircle.visibility == View.VISIBLE) {
                //stormButton_start_host_round_setting.visibility = View.INVISIBLE
                //goToFragment(HostRoundSettingFragment::class.java, null)

                //stormButton_start_host_round_setting.visibility = View.GONE
                //stormButton_ok_host_round_setting.visibility = View.VISIBLE


                //stormButton_ok_host_round_setting.setOnClickListener {
                    if (textview_round_goal.text.isNullOrBlank() || edittext_round_time.text.isNullOrBlank()) {
                        Toast.makeText(this, "라운드 목표 혹은 라운드 소요시간을 입력해주세요", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        goToFragment(RoundStartFragment::class.java, null)
                        stormButton_start_host_round_setting.visibility = View.VISIBLE
                        stormButton_ok_host_round_setting.visibility = View.GONE


                        stormButton_start_host_round_setting.setOnClickListener {

                            StormDialogBuilder(StormDialogBuilder.LOADING_LOGO, "5초 후 라운드가 시작합니다")
                                .build()
                                .show(supportFragmentManager, "waiting")

                            val handler = Handler()
                            val handlerTask = object : Runnable {
                                override fun run() {
                                    val intent = Intent(this@RoundSettingActivity,RoundProgressActivity::class.java)
                                    intent.putExtra("라운드 목표",textview_round_goal.text.toString())
                                    intent.putExtra("라운드 소요 시간",edittext_round_time.text.toString())
                                    startActivity(intent)
                                }
                            }

                            handler.postDelayed(handlerTask, 5000)


                        }

                    }
                //}
            //}

        }
    }

    private fun addFragment(fragment: Fragment) {
        // val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        // transaction.replace(R.id.constraint_host_round, fragment).commitAllowingStateLoss()
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

}
package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils.replace
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.round.adapter.RoundPagerAdapter
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.RoundSettingWaitingMemberFragment
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*

class HostRoundWaitingActivity : BaseActivity() {
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        goToFragment(WaitingForStartingProjectFragment::class.java, null)

        stormButton_start_host_round_setting.setActivation(false)

        stormButton_start_host_round_setting.setOnClickListener {
            if (imageview_checkrules_checkcircle.visibility == View.VISIBLE) {
                stormButton_start_host_round_setting.visibility = View.INVISIBLE
                goToFragment(HostRoundSettingFragment::class.java, null)

                stormButton_start_host_round_setting.visibility = View.GONE
                stormButton_ok_host_round_setting.visibility = View.VISIBLE


                stormButton_ok_host_round_setting.setOnClickListener {
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
                                    val intent = Intent(this@HostRoundWaitingActivity,RoundProgressActivity::class.java)
                                    intent.putExtra("라운드 목표",textview_round_goal.text.toString())
                                    intent.putExtra("라운드 소요 시간",edittext_round_time.text.toString())
                                    startActivity(intent)
                                }
                            }

                            handler.postDelayed(handlerTask, 5000)


                        }

                    }
                }
            }

        }
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }



}
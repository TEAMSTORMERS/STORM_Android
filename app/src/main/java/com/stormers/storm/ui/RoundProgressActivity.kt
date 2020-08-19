package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.stormers.storm.R
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.round.base.BaseRoundProgressActivity
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RoundProgressActivity : BaseRoundProgressActivity() {

    val cardList = mutableListOf<CardEnumModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Debug 용도로 라운드 목표를 터치하면 라운드가 종료되도록 함
        this.textView_round_goal.setOnClickListener {
            startActivity(Intent(this, RoundFinishActivity::class.java))
            finish()
        }

        val roundTime: Long = (roundTime * 1000 * 60).toLong()

        countDown(roundTime)

        goToFragment(AddCardFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }

    private fun countDown(time:Long) {
        val countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                val millis: Long = p0
                val hms = String.format(
                    "총 %02d:%02d 남음", //(p0/(1000*60)%60), ((p0/1000)%60)
                    (TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
                    (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    ))
                )
                textView_time.text = hms
            }

            override fun onFinish() {
                startActivity(Intent(this@RoundProgressActivity, RoundFinishActivity::class.java))
                finish()
            }
        }
        countDownTimer.start()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments[0] is AddCardFragment) {
            val buttonList = ArrayList<StormDialogButton>()
            buttonList.add(StormDialogButton("취소", true, null))
            buttonList.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    //Todo: 프로젝트 나가기
                    finish()
                }
            }))
            StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "프로젝트에서 나가시겠습니까?")
                .setHorizontalArray(buttonList)
                .build()
                .show(supportFragmentManager, "exit")
        } else {
            goToFragment(AddCardFragment::class.java, null)
        }
    }
}
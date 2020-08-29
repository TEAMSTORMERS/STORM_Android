package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.canvas.fragment.CanvasTextFragment
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.card.model.CacheCardModel
import com.stormers.storm.round.base.BaseRoundProgressActivity
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.util.concurrent.TimeUnit

class RoundProgressActivity : BaseRoundProgressActivity() {

    val cardList = mutableListOf<CacheCardModel>()

    private lateinit var countDownTimer: CountDownTimer

    var addCardFragment: Fragment? = null
    var canvasDrawingFragment: Fragment? = null
    var canvasTextFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Debug 용도로 라운드 목표를 터치하면 라운드가 종료되도록 함
        this.textView_round_goal.setOnClickListener {
            countDownTimer.cancel()
            startActivity(Intent(this, HostRoundFinishActivity::class.java))
            finish()
        }

        val roundTime: Long = (roundTime * 1000 * 60).toLong()

        countDown(roundTime)

        addCardFragment = goToFragment(AddCardFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }

    private fun countDown(time:Long) {
        countDownTimer = object : CountDownTimer(time, 1000) {
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
                if (GlobalApplication.isHost) {
                    this@RoundProgressActivity.startActivity(Intent(this@RoundProgressActivity, HostRoundFinishActivity::class.java))
                } else {
                    this@RoundProgressActivity.startActivity(Intent(this@RoundProgressActivity, MemberRoundFinishActivity::class.java))
                }
                this@RoundProgressActivity.finish()
            }
        }
        countDownTimer.start()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments[0] is AddCardFragment) {
            return
        } else {
            goToFragment(AddCardFragment::class.java, null)
        }
    }
}
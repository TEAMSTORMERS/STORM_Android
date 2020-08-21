package com.stormers.storm.round.base

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.stormers.storm.R
import com.stormers.storm.card.fragment.RoundMeetingFragment
import com.stormers.storm.network.SocketClient
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.lang.StringBuilder

open class BaseRoundFinishActivity : BaseRoundProgressActivity() {

    protected lateinit var finishButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        finishButton = button_roundprogress_finish

        goToFragment(RoundMeetingFragment::class.java, null)

        setRoundTime()

        stormtoolbar_roundprogress.setExitButton(View.OnClickListener {
            //Todo: 프로젝트 나가기
        })
    }

    private fun setRoundTime() {
        val time = StringBuilder("총 ")
        if (roundTime < 10) {
            time.append(0)
        }
        time.append(roundTime)
            .append(":00 소요")
        textView_time.text = time.toString()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }
}
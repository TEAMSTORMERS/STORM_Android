package com.stormers.storm.round.base

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.stormers.storm.R
import com.stormers.storm.card.fragment.RoundMeetingFragment
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.lang.StringBuilder

open class BaseRoundFinishActivity : BaseRoundProgressActivity() {

    companion object {
        private const val TAG = "BaseRoundFinishActivity"

        const val REQUEST_EXPAND = 100

        const val RESULT_DIRTY = 10
    }

    protected lateinit var finishButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        finishButton = button_roundprogress_finish

        goToFragment(RoundMeetingFragment::class.java, null)

        setRoundTime()
    }

    private fun setRoundTime() {
        val time = StringBuilder("총 ")
        time.append(roundTime)
            .append("분 소요")
        textView_time.text = time.toString()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }
}
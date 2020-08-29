package com.stormers.storm.round.base

import android.os.Bundle
import android.widget.Button
import com.stormers.storm.R
import com.stormers.storm.card.fragment.RoundMeetingFragment
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.lang.StringBuilder

open class BaseRoundFinishActivity : BaseRoundProgressActivity() {

    companion object {
        private const val TAG = "BaseRoundFinishActivity"
    }

    protected lateinit var finishButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        finishButton = button_roundprogress_finish

        goToFragment(RoundMeetingFragment::class.java, null)

        setRoundTime()

        setExitButton()
    }

    override fun onExitDialogPositiveClick() {
        super.onExitDialogPositiveClick()
        exitRound()
    }

    private fun setRoundTime() {
        val time = StringBuilder("총 ")
        if (roundTime < 10) {
            time.append(0)
        }
        time.append(roundTime)
            .append("분 소요")
        textView_time.text = time.toString()
    }

    //라운드 나가기
    private fun exitRound() {
        //소켓으로 나감을 알림
        leaveSocket()

        //종료
        finish()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }
}
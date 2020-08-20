package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.RoundMeetingFragment
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.base.BaseRoundProgressActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_progress.*
import java.lang.StringBuilder

class RoundFinishActivity : BaseRoundProgressActivity() {

    private var buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goToFragment(RoundMeetingFragment::class.java, null)

        setRoundTime()

        stormtoolbar_roundprogress.setExitButton(View.OnClickListener {
            //Todo: 프로젝트 나가기
        })

        SocketClient.getInstance()
        SocketClient.connection()

        button_scrapcard_save_roundmeeting.visibility = View.VISIBLE

        initDialogButton()

        initDialog()

        if (GlobalApplication.isHost) {

            button_scrapcard_save_roundmeeting.visibility = View.VISIBLE

            button_scrapcard_save_roundmeeting.setOnClickListener {
                dialog.show(supportFragmentManager, "roundfinish")
            }
        } else {
            button_scrapcard_save_roundmeeting.visibility = View.GONE

            waitNextRound()
        }
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

    private fun initDialogButton() {
        buttonArray.add(
            StormDialogButton("다음 ROUND 진행", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    startNextRound()
                }
            })
        )
        buttonArray.add(
            StormDialogButton("프로젝트 종료 후 최종 정리", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    finishRound()
                }
            })
        )

    }

    private fun startNextRound() {
        SocketClient.sendEvent("nextRound", GlobalApplication.currentProject!!.projectCode!!)

        startActivity(Intent(this@RoundFinishActivity, RoundSettingActivity::class.java))
        finish()
    }

    private fun finishRound() {
        SocketClient.sendEvent("finishProject", GlobalApplication.currentProject!!.projectCode!!)

        startDetailActivity()
    }

    private fun waitNextRound() {
        SocketClient.responseEvent("memberNextRound", Emitter.Listener {
            startActivity(Intent(this@RoundFinishActivity, MemberRoundWaitingActivity::class.java))
            finish()
        })

        SocketClient.responseEvent("memberFinishProject", Emitter.Listener {
            startDetailActivity()
        })
    }

    private fun startDetailActivity () {
        val intent = Intent(this@RoundFinishActivity, ParticipatedProjectDetailActivity::class.java)
        intent.putExtra("projectIdx", GlobalApplication.currentProject!!.projectIdx)

        startActivity(intent)
        finish()
    }

    private fun initDialog() {
        val round = StringBuilder()
        round.append("ROUND ")
            .append(GlobalApplication.currentRound!!.roundNumber)
            .append(" 종료")

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, round.toString())
            .setButtonArray(buttonArray)
            .build()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }


}
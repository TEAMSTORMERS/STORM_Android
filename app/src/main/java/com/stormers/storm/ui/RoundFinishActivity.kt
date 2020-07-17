package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.RoundmeetingFragment
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.SocketClient
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*
import java.lang.StringBuilder

class RoundFinishActivity : BaseActivity() {

    private var buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        SocketClient.getInstance()
        SocketClient.connection()

        button_scrapcard_save_roundmeeting.visibility = View.VISIBLE

        goToFragment(RoundmeetingFragment::class.java, null)

        initDialogButton()

        initDialog()

        if (preference.isHost()) {

            button_scrapcard_save_roundmeeting.visibility = View.VISIBLE

            button_scrapcard_save_roundmeeting.setOnClickListener {
                dialog.show(supportFragmentManager, "roundfinish")
            }
        } else {
            button_scrapcard_save_roundmeeting.visibility = View.GONE

            waitNextRound()
        }
    }

    override fun onResume() {
        super.onResume()

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
        SocketClient.sendEvent("nextRound", preference.getProjectCode()!!)

        startActivity(Intent(this@RoundFinishActivity, RoundSettingActivity::class.java))
        finish()
    }

    private fun finishRound() {
        SocketClient.sendEvent("finishProject", preference.getProjectCode()!!)

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
        intent.putExtra("projectIdx", preference.getProjectIdx())

        startActivity(intent)
        finish()
    }

    private fun initDialog() {
        val round = StringBuilder()
        round.append("ROUND ")
            .append(preference.getRoundCount())
            .append(" 종료")

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, round.toString())
            .setButtonArray(buttonArray)
            .build()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }


}
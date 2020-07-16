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
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*
import java.lang.StringBuilder

class RoundFinishActivity : BaseActivity() {

    private var buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        goToFragment(RoundmeetingFragment::class.java, null)

        initDialogButton()

        initDialog()

        //if (userIdx = "host") {

            button_scrapcard_save_roundmeeting.visibility = View.VISIBLE

            button_scrapcard_save_roundmeeting.setOnClickListener {
                dialog.show(supportFragmentManager, "roundfinish")
         //   }
        }

    }

    private fun initDialogButton() {
        val buttonArray = ArrayList<StormDialogButton>()

        buttonArray.add(
            StormDialogButton("다음 ROUND 진행", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    startActivity(Intent(this@RoundFinishActivity, RoundSettingActivity::class.java))
                }
            })
        )
        buttonArray.add(
            StormDialogButton("프로젝트 종료 후 최종 정리", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    val intent = Intent(this@RoundFinishActivity, ParticipatedProjectDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        )

    }

    private fun initDialog() {
        val round = StringBuilder()
        round.append("ROUND ")
            .append(preference.getRoundIdx())
            .append(" 종료")

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, round.toString())
            .setButtonArray(buttonArray)
            .build()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }


}
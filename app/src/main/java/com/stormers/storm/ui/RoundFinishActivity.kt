package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.RoundmeetingFragment
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*

class RoundFinishActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)
        goToFragment(RoundmeetingFragment::class.java, null)

        button_scrapcard_save_roundmeeting.visibility = View.VISIBLE

        button_scrapcard_save_roundmeeting.setOnClickListener{
            val buttonArray = ArrayList<StormDialogButton>()

            buttonArray.add(
                StormDialogButton("다음 ROUND 진행", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        val intent = Intent(this@RoundFinishActivity, RoundSettingActivity::class.java)
                        startActivity(intent)

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

            StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "ROUND 1 종료")
                .setButtonArray(buttonArray)
                .build()
                .show(supportFragmentManager, "roundfinish")

        }

    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }


}
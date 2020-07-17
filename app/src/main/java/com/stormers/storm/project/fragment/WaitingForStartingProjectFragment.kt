package com.stormers.storm.project.fragment

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.MemberWaitingFragment
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*

class WaitingForStartingProjectFragment : BaseWaitingFragment(R.layout.fragment_waiting_for_starting_project) {

    private lateinit var activityButton: StormButton

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        activityButton = (activity as BaseProjectWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            setText("시작")
            setActivation(false)

//            setOnClickListener {
//                if ((activity as BaseProjectWaitingActivity).isHost) {
//                    goToFragment(HostRoundSettingFragment::class.java, null)
//                } else {
//                    goToFragment(MemberWaitingFragment::class.java, null)
//                }
//            }
        }

        buttonArray.add(
            StormDialogButton("확인", false, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    imageview_waitingproject_checkcircle.setImageResource(R.drawable.h_projectwaiting_brainstorming_ok_selected)

                    activityButton.setActivation(true)
                }
            })
        )

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "브레인스토밍 룰 리마인더")
                .setContentRes(R.layout.view_rule_reminder)
                .setHorizontalArray(buttonArray)
                .build()

        cardview_waitingproject_checkrules.setOnClickListener {
            fragmentManager?.let { it1 -> dialog.show(it1, "rule_reminder") }
        }
    }
}
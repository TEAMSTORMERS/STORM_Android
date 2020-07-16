package com.stormers.storm.round.fragment

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.round.base.BaseWaitingFragment
import kotlinx.android.synthetic.main.activity_round_setting.*


class MemberWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_setting_waiting_member) {

    private lateinit var activityButton: StormButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityButton = (activity as BaseProjectWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            visibility = View.GONE
        }

        //Todo: 소켓통신으로 호스트가 준비 완료하면 라운드 시작해야함 ~~

    }
}
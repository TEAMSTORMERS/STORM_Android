package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import kotlinx.android.synthetic.main.activity_round_setting.*

class RoundSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        stormtoolbar_roundsetting.setExitButton(View.OnClickListener {
            //Todo: 프로젝트 나가기
        })

        //상단 카드 설정
        textview_projectcard_title.text = GlobalApplication.currentProject!!.projectName

        goToFragment(HostRoundSettingFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }
}
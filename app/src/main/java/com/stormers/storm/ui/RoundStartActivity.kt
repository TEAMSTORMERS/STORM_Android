package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.round.fragment.RoundStartFragment
import kotlinx.android.synthetic.main.activity_round_setting.*

class RoundStartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        stormtoolbar_roundsetting.setExitButton(View.OnClickListener {
            //Todo: 라운드 나가기
        })

        goToFragment(RoundStartFragment::class.java, null)

    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }
}
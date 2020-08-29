package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.HostRoundWaitingFragment
import kotlinx.android.synthetic.main.activity_round_setting.*

class HostRoundSettingActivity : BaseRoundWaitingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //화면 밀어 올리기
        scrollViewKeyBoard(scrollview_round_setting)

        //멤버가 호스트가 되어서 해당 액티비티에 왔는지 확인
        val isPromotion = intent.getBooleanExtra("isPromotion", false)

        //기존 호스트가 해당 액티비티에 도착한 경우
        if (!isPromotion) {
            //라운드 세팅 프래그먼트로 전환
            goToFragment(HostRoundSettingFragment::class.java, null)

        //멤버가 호스트가 되어서 해당 액티비티에 도착한 경우
        } else {
            //관련 Bundle과 함께 라운드 대기방으로 이동
            goToFragment(HostRoundWaitingFragment::class.java, Bundle().apply {
                putBoolean("isPromotion", isPromotion)
            })
        }
    }
}
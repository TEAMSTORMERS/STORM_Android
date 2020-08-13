package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mypage_withdrawal_check.*

class MypageWithdrawalCheckActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_withdrawal_check)

        stormtoolbar_mypage_withdrawal_check.setBackButton()

    }
}
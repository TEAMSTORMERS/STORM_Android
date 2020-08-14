package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mypage_withdrawal.*

class MypageWithdrawalActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_withdrawal)

        stormbutton_withdrawal_next.setOnClickListener {
            val nextIntent = Intent(this, MypageWithdrawalCheckActivity::class.java)
            startActivity(nextIntent)
        }

        stormtoolbar_mypage_withdrawal.setBackButton()

    }

}
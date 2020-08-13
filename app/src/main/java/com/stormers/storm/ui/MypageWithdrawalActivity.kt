package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_mypage_withdrawal.*

class MypageWithdrawalActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_withdrawal)

        stormbutton_withdrawal_next.setOnClickListener {
            val nextIntent = Intent(this, MypageWithdrawalCheckActivity::class.java)
            startActivity(nextIntent)
        }

        edittext_withdrawal_reason.addTextChangedListener(textWatcher)

        stormtoolbar_mypage_withdrawal.setBackButton()

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val length = s.toString().length
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (edittext_withdrawal_reason.lineCount > 5) {
                edittext_withdrawal_reason.setText(s?.dropLast(1).toString()) // 5줄 초과 시 마지막 한글자 삭제
                edittext_withdrawal_reason.setSelection(s?.length!! - 1) // 커서 위치 마지막으로 이동
            }
        }

    }
}
package com.stormers.storm.mypage.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mypage_withdrawal.*

class MypageWithdrawalFragment : BaseFragment(R.layout.fragment_mypage_withdrawal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stormbutton_withdrawal_next.setOnClickListener {
            goToFragment(MypageWithdrawalCheckFragment::class.java, null)
        }

        edittext_withdrawal_reason.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val length = s.toString().length
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (edittext_withdrawal_reason.lineCount > 5) {
                edittext_withdrawal_reason.setText(s?.dropLast(1).toString()) // 2줄 초과 시 마지막 한글자 삭제
                edittext_withdrawal_reason.setSelection(s?.length!! - 1) // 커서 위치 마지막으로 이동
            }
        }

    }
}
package com.stormers.storm.mypageedittext_user_name.fragment

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mypage_profile.*

class MypageProfileFragment : BaseFragment(R.layout.fragment_mypage_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagebutton_mypage_edit.setOnClickListener {
            textview_user_name_input.visibility = View.INVISIBLE
            edittext_user_name.visibility = View.VISIBLE
        }
    }
}
package com.stormers.storm.ui

import android.os.Bundle
import android.view.WindowManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.mypage.fragment.MypageProfileFragment
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        goToFragment(MypageProfileFragment::class.java, null)

        stormtoolbar_mypage.setBackButton()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_mypage
    }

}
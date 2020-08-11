package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.mypage.fragment.MypageProfileFragment

class MypageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        goToFragment(MypageProfileFragment::class.java, null)

    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_mypage
    }

}
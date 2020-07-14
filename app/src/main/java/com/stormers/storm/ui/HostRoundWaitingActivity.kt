package com.stormers.storm.ui

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment

class HostRoundWaitingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        goToFragment(WaitingForStartingProjectFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
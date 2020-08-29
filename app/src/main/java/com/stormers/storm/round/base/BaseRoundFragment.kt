package com.stormers.storm.round.base

import android.content.Context
import android.util.Log
import androidx.annotation.LayoutRes
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.ui.GlobalApplication


abstract class BaseRoundFragment(@LayoutRes layoutRes: Int): BaseFragment(layoutRes) {

    protected val userIdx = GlobalApplication.userIdx
    protected val projectIdx = GlobalApplication.currentProject!!.projectIdx
    protected val roundIdx = GlobalApplication.currentRound?.roundIdx
    protected val roundNumber = GlobalApplication.currentRound?.roundNumber

    protected var mActivity: OnProjectActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivity = context as OnProjectActivity

        if (mActivity == null) {
            Log.e(TAG, "mActivity is null.")
            return
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity = null
    }

    open fun onExitRound() {
        Log.d(TAG, "onExitRound()")
    }
}
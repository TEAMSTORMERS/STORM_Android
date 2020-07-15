package com.stormers.storm.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.InterfaceProjectInfo
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.project.model.ResponseProjectInfoModel
import com.stormers.storm.project.network.ProjectInterface
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostRoundWaitingActivity : BaseActivity() {

    private var projectIdx = -1

    private lateinit var retrofitClient: InterfaceProjectInfo

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_round_setting)

        goToFragment(WaitingForStartingProjectFragment::class.java, null)

        /* Todo: AddProjectActivity에서 입력했던 projectName이 현재 액티비티의 textview_projectcard_title에 들어가도록
                서버와 통신환경 구축*/

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
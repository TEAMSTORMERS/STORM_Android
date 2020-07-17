package com.stormers.storm.project.base

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
import com.stormers.storm.round.fragment.MemberWaitingFragment
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseProjectWaitingActivity : BaseActivity() {

    private lateinit var retrofitClient: InterfaceProjectInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        goToFragment(MemberWaitingFragment::class.java, null)

        retrofitClient = RetrofitClient.create(InterfaceProjectInfo::class.java)

        preference.getProjectIdx()?.let {

            retrofitClient.responseProjectinfo(it).enqueue(object :
                Callback<ResponseProjectInfoModel> {
                override fun onFailure(call: Call<ResponseProjectInfoModel>, t: Throwable) {

                    Log.d("ProjectInfo 통신실패", "${t}")
                }

                override fun onResponse(
                    call: Call<ResponseProjectInfoModel>,
                    response: Response<ResponseProjectInfoModel>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d("ProjectName 통신성공", "통신성공")
                            textview_projectcard_title.text = response.body()!!.data.projectName
                            preference.setProjectName(response.body()!!.data.projectName)
                            //Todo: 라운드 참여 
                        }
                    }
                }
            })

        }
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
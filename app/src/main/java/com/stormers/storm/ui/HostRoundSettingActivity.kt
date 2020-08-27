package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.round.base.BaseRoundWaitingActivity
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.fragment.HostRoundWaitingFragment
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostRoundSettingActivity : BaseRoundWaitingActivity() {

    companion object {
        private const val TAG = "HostRoundSettingActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isPromotion = intent.getBooleanExtra("isPromotion", false)

        if (!isPromotion) {
            goToFragment(HostRoundSettingFragment::class.java, null)
        } else {
            goToFragment(HostRoundWaitingFragment::class.java, Bundle().apply {
                putBoolean("isPromotion", isPromotion)
            })
        }
        scrollViewKeyBoard(scrollview_round_setting)
    }

    override fun onExitBeforeFirstRound() {
        super.onExitBeforeFirstRound()
        //프로젝트 삭제
        deleteProject()
    }

    //라운드 종료할 때 (2라운드 이상)
    override fun onLeaveRound() {
        super.onLeaveRound()

        //프로젝트 종료
        finishProject()
    }

    @SuppressLint("LongLogTag")
    private fun finishProject() {
        SocketClient.sendEvent(SocketClient.FINISH_PROJECT, GlobalApplication.currentProject!!.projectCode!!)
        Log.d(TAG, "[socket] finishProject: ${GlobalApplication.currentProject!!.projectCode!!}")

        requestFinishProject()

        startDetailActivity()
    }

    private fun startDetailActivity () {
        val intent = Intent(this, ParticipatedProjectDetailActivity::class.java)
        intent.putExtra("projectIdx", GlobalApplication.currentProject!!.projectIdx)
        intent.putExtra("isAfterProject", true)

        SocketClient.disconnectionAndClose()

        startActivity(intent)
        finish()
    }

    private fun requestFinishProject() {
        RetrofitClient.create(RequestProject::class.java).finishProject(GlobalApplication.currentProject!!.projectIdx)
            .enqueue(object: Callback<SimpleResponse> {
                @SuppressLint("LongLogTag")
                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    Log.d(TAG, "requestFinishProject: Fail, ${t.message}")
                }

                @SuppressLint("LongLogTag")
                override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "requestFinishProject: Success")
                        } else {
                            Log.d(TAG, "requestFinishProject: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "requestFinishProject: Not successful, ${response.message()}")
                    }
                }
            })
    }

    @SuppressLint("LongLogTag")
    private fun deleteProject() {
        Log.d(TAG, "deleteProject: ${GlobalApplication.currentProject!!.projectIdx}")
        RetrofitClient.create(RequestProject::class.java).deleteProject(GlobalApplication.currentProject!!.projectIdx)
            .enqueue(object: Callback<SimpleResponse> {

            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d(TAG, "deleteProject: Fail, ${t.message}")
            }

            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "deleteProject: Success.")
                        finish()
                    } else {
                        Log.d(TAG, "deleteProject: Not success, ${response.body()!!.message}")
                    }
                } else {
                    Log.d(TAG, "deletePropject: Not successful, ${response.message()}")
                }
            }
        })
    }
}
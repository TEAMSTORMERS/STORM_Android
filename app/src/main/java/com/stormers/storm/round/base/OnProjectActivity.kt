package com.stormers.storm.round.base

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.StormToolbar
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.ParticipatedProjectDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class OnProjectActivity: BaseActivity() {

    protected val userIdx = GlobalApplication.userIdx
    protected val projectIdx = GlobalApplication.currentProject!!.projectIdx
    protected val roundIdx = GlobalApplication.currentRound?.roundIdx
    protected val projectCode = GlobalApplication.currentProject!!.projectCode!!

    private var toolbar: StormToolbar? = null

    private val exitDialogButtons: ArrayList<StormDialogButton> by lazy { ArrayList<StormDialogButton>() }

    /**
     * Toolbar 초기화 추상 메서드
     * 각 액티비티의 StormToolbar를 리턴하여야 함
     */
    protected fun setStormToolbar(toolbar: StormToolbar) {
        this.toolbar = toolbar
    }

    /**
     * 프로젝트를 종료하고 싶을 때 호출하는 메서드
     * 1. FINISH_PROJECT 소켓 이벤트를 Emit
     * 2. 프로젝트 종료 HTTP 통신
     * 3. 최종 정리뷰로 이동
     */
    fun finishProject() {
        SocketClient.sendEvent(SocketClient.FINISH_PROJECT, projectCode)
        Log.d(TAG, "[socket] finishProject: $projectCode")

        requestFinishProject()

        startDetailActivity()
    }

    /**
     * 프로젝트를 삭제하고 싶을 때 호출하는 메서드
     * 1. 프로젝트 삭제 HTTP 통신
     * 2. 액티비티 종료
     */
    fun deleteProject() {
        requestDeleteProject()
    }

    /**
     * 나가기 버튼을 활성화
     */
    fun setExitButton() {
        toolbar?.setExitButton(View.OnClickListener {
            showExitDialog()
        })
    }

    /**
     * 현재 프래그먼트 가져오기
     */
    protected fun getCurrentFragment(): BaseRoundFragment? {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                return fragment as BaseRoundFragment
            }
        }
        return null
    }

    /**
     * 나가기 다이얼로그에서 확인 버튼을 눌렀을 때
     */
    protected open fun onExitDialogPositiveClick() {
        Log.d(TAG, "onExitDialogPositiveClick() : Clicked")
    }

    //나가기 다이얼로그 띄우기
    private fun showExitDialog() {
        if (exitDialogButtons.isEmpty()) {
            exitDialogButtons.add(StormDialogButton("취소", true, null))
            exitDialogButtons.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    onExitDialogPositiveClick()
                }
            }))
        }

        StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "프로젝트를 나가시겠습니까?")
            .setHorizontalArray(exitDialogButtons)
            .build()
            .show(supportFragmentManager, "exit_round")
    }

    private fun requestFinishProject() {
        RetrofitClient.create(RequestProject::class.java).finishProject(projectIdx)
            .enqueue(object: Callback<SimpleResponse> {
                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    Log.d(TAG, "requestFinishProject: Fail, ${t.message}")
                }

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

    /**
     * 최종 정리뷰로 이동
     * 1. 인텐트 값 세팅
     * 2. 소켓 연결 끊기
     * 3. 화면 전환
     */
    fun startDetailActivity () {
        val intent = Intent(this, ParticipatedProjectDetailActivity::class.java)
        intent.putExtra("projectIdx", projectIdx)
        intent.putExtra("isAfterProject", true)

        SocketClient.disconnectionAndClose()

        startActivity(intent)
        finish()
    }

    @SuppressLint("LongLogTag")
    private fun requestDeleteProject() {
        Log.d(TAG, "deleteProject: $projectIdx")
        RetrofitClient.create(RequestProject::class.java).deleteProject(projectIdx)
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

                            //응답 후에 종료하여야 메인 화면에서 해당 프로젝트가 뜨지 않음
                            finish()
                        } else {
                            Log.d(TAG, "deleteProject: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "deleteProject: Not successful, ${response.message()}")
                    }
                }
            })
    }
}
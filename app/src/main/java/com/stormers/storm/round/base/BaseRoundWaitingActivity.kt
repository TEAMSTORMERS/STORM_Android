package com.stormers.storm.round.base

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRoundWaitingActivity : BaseActivity() {

    companion object {
        private const val TAG = "BaseRoundWaitingActivity"
    }

    private val exitDialogButtons: ArrayList<StormDialogButton> by lazy { ArrayList<StormDialogButton>() }

    private val userIdx = GlobalApplication.userIdx
    private val projectIdx = GlobalApplication.currentProject?.projectIdx
    private val roundIdx = GlobalApplication.currentRound?.roundIdx


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        stormtoolbar_roundsetting.setExitButton(View.OnClickListener {
            showExitDialog()
        })

        //상단 카드 설정
        textview_projectcard_title.text = GlobalApplication.currentProject!!.projectName

        constraintlayout_roundsetting_projectcard.setOnClickListener {
            val clipboardManager = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clipData = ClipData.newPlainText("participate_code", GlobalApplication.currentProject!!.projectCode);

            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(application, "참여코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    //라운드 나가기 통신
    @SuppressLint("LongLogTag")
    private fun exitRound() {
        Log.d(TAG, "exitRound(): userIdx: $userIdx, projectIdx: $projectIdx, roundIdx: $roundIdx")
        RetrofitClient.create(RequestRound::class.java).exitRound(userIdx, projectIdx!!, roundIdx!!)
            .enqueue(object : Callback<SimpleResponse> {

                @SuppressLint("LongLogTag")
                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    Log.d(TAG, "exitRound: Fail. ${t.message}")
                }

                @SuppressLint("LongLogTag")
                override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "exitRound: Success.")



                            //소켓으로 나감을 알림
                            leaveSocket()

                            //종료
                            finish()
                        } else {
                            Log.d(TAG, "exitRound: Not success. ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "exitRound: Not successful. ${response.message()}")
                    }
                }
            })
    }

    //나가기 다이얼로그 띄우기
    private fun showExitDialog() {
        if (exitDialogButtons.isEmpty()) {
            exitDialogButtons.add(StormDialogButton("취소", true, null))
            exitDialogButtons.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    //roundIdx가 존재할 때 -> 라운드가 한 번이라도 생성되었을 때
                    roundIdx?.let {
                        //라운드 나가기
                        exitRound()

                    }?: onExitBeforeFirstRound() //roundIdx가 null일 때 -> 첫 번째 라운드 생성 전
                }
            }))
        }

        StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "프로젝트를 나가시겠습니까?")
            .setHorizontalArray(exitDialogButtons)
            .build()
            .show(supportFragmentManager, "exit_round")

    }

    private fun leaveSocket() {
        SocketClient.sendEvent(SocketClient.LEAVE_ROOM, GlobalApplication.currentProject!!.projectCode!!)
        SocketClient.disconnectionAndClose()
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

    @SuppressLint("LongLogTag")
    protected open fun onExitBeforeFirstRound() {
        Log.d(TAG, "onExitRound()")
    }

    @SuppressLint("LongLogTag")
    protected open fun onLeaveRound() {
        Log.d(TAG, "onLeaveRound()")
    }
}
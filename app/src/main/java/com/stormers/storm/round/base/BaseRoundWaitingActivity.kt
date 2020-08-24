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
import com.stormers.storm.util.KeyBoardVisibilityUtils
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRoundWaitingActivity : BaseActivity() {

    companion object {
        private const val TAG = "BaseRoundWaitingActivity"
    }

    private val exitDialogButtons: ArrayList<StormDialogButton> by lazy { ArrayList<StormDialogButton>() }

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
    private fun exitRound() {
        RetrofitClient.create(RequestRound::class.java).exitRound(GlobalApplication.userIdx,
            GlobalApplication.currentProject!!.projectIdx, GlobalApplication.currentRound!!.roundIdx)
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
                    exitRound()
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
}
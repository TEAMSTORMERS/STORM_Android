package com.stormers.storm.round.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.stormers.storm.R
import com.stormers.storm.card.fragment.RoundMeetingFragment
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.ParticipatedProjectDetailActivity
import kotlinx.android.synthetic.main.activity_round_progress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

open class BaseRoundFinishActivity : BaseRoundProgressActivity() {

    companion object {
        private const val TAG = "BaseRoundFinishActivity"
    }

    protected lateinit var finishButton: Button

    private val exitDialogButtons: ArrayList<StormDialogButton> by lazy { ArrayList<StormDialogButton>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        finishButton = button_roundprogress_finish

        goToFragment(RoundMeetingFragment::class.java, null)

        setRoundTime()

        stormtoolbar_roundprogress.setExitButton(View.OnClickListener {
            showExitDialog()
        })
    }

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

    private fun setRoundTime() {
        val time = StringBuilder("총 ")
        if (roundTime < 10) {
            time.append(0)
        }
        time.append(roundTime)
            .append(":00 소요")
        textView_time.text = time.toString()
    }

    protected fun startDetailActivity () {
        val intent = Intent(this, ParticipatedProjectDetailActivity::class.java)
        intent.putExtra("projectIdx", GlobalApplication.currentProject!!.projectIdx)
        intent.putExtra("isAfterProject", true)

        SocketClient.disconnectionAndClose()

        startActivity(intent)
        finish()
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

    private fun leaveSocket() {
        SocketClient.sendEvent(SocketClient.LEAVE_ROOM, GlobalApplication.currentProject!!.projectCode!!)
        SocketClient.disconnectionAndClose()
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }
}
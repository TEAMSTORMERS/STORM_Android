package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.BaseResponse
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.base.BaseRoundFinishActivity
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.network.RequestRound
import io.socket.emitter.Emitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberRoundFinishActivity : BaseRoundFinishActivity() {

    companion object {
        private const val TAG = "MRoundFinishActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        waitNextRoundOrFinish()
    }

    private fun waitNextRoundOrFinish() {
        SocketClient.responseEvent(SocketClient.WAIT_NEXT_ROUND, Emitter.Listener {

            SocketClient.offEvent(SocketClient.WAIT_NEXT_ROUND)
            SocketClient.offEvent(SocketClient.MEMBER_FINISH_PROJECT)
            Log.d(TAG, "[socket] waitNextRound: Host is setting next round......")

            //UI를 변경하기 때문에 runOnUiThread로 둘러싸줌
            runOnUiThread {
                finishButton.run {
                    text = context.getString(R.string.prepare_next_round)
                    visibility = View.VISIBLE
                }
            }
        })

        Log.d(TAG, "[socket] waitNextRound: set")

        SocketClient.responseEvent(SocketClient.MEMBER_NEXT_ROUND, Emitter.Listener {

            SocketClient.offEvent(SocketClient.MEMBER_NEXT_ROUND)
            Log.d(TAG, "[socket] memberNextRound: Go to next round!!")
            //showNextRoundDialog()
            enterNextRound()
        })

        Log.d(TAG, "[socket] memberNextRound: set")

        SocketClient.responseEvent(SocketClient.MEMBER_FINISH_PROJECT, Emitter.Listener {
            Log.d(TAG, "[socket] memberFinishProject: It's enough. Go to finish")
            startDetailActivity()
        })

        Log.d(TAG, "[socket] memberFinishProject: set")
    }

    private fun showNextRoundDialog() {
        val button = ArrayList<StormDialogButton>()
        button.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
            override fun onClick() {
                enterNextRound()
            }
        }))

        StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "Round ${GlobalApplication.currentRound!!.roundNumber} 종료")
            .setContentText("다음 단계로 이동합니다.")
            .setButtonArray(button)
            .build()
            .show(supportFragmentManager, "go_to_next_round")
    }

    private fun goToNextRound() {
        val intent = Intent(this@MemberRoundFinishActivity, MemberRoundWaitingActivity::class.java)
        intent.putExtra("isFirstRound", false)
        startActivity(intent)
        finish()
    }


    private fun enterNextRound(){
        val projectIdx = GlobalApplication.currentProject!!.projectIdx
        val userIdx = GlobalApplication.userIdx

        Log.d(TAG, "enterRound: userIdx: $userIdx")
        Log.d(TAG, "enterRound: projectIdx: $projectIdx")

        RetrofitClient.create(RequestRound::class.java).enterNextRound((RoundEnterModel(userIdx, projectIdx)))
            .enqueue(object  : Callback<BaseResponse> {

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Log.d(TAG, "enterRound: fail, ${t.message}")
                }

                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "enterRound: success")

                            GlobalApplication.currentRound!!.roundIdx = response.body()!!.data

                            goToNextRound()

                        } else {
                            Log.d(TAG, "enterRound: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "enterRound: Not successful, ${response.message()}")
                    }
                }
            })
    }
}
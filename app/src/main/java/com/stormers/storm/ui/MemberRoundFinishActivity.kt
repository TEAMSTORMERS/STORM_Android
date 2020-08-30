package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.network.BaseResponse
import com.stormers.storm.network.RetrofitClient
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

        setExitButton()

        waitNextRoundOrFinish()
    }

    override fun onExitDialogPositiveClick() {
        super.onExitDialogPositiveClick()
        exitRound()
    }

    //라운드 나가기
    private fun exitRound() {
        //소켓으로 나감을 알림
        leaveSocket()

        //종료
        finish()
    }

    private fun waitNextRoundOrFinish() {

        //호스트가 다음 라운드를 준비할 때
        SocketClient.responseEvent(SocketClient.WAIT_NEXT_ROUND, Emitter.Listener {

            //동일한 신호를 기다리지 않음
            SocketClient.offEvent(SocketClient.WAIT_NEXT_ROUND)
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

        //호스트가 다음 라운드 세팅을 마쳤을 때
        SocketClient.responseEvent(SocketClient.MEMBER_NEXT_ROUND, Emitter.Listener {

            //동일 신호를 기다리지 않음
            SocketClient.offEvent(SocketClient.MEMBER_NEXT_ROUND)
            Log.d(TAG, "[socket] memberNextRound: Go to next round!!")

            //다음 라운드로 이동
            enterNextRound()
        })
        Log.d(TAG, "[socket] memberNextRound: set")

        //호스트가 나가거나 프로젝트를 종료할 때
        SocketClient.responseEvent(SocketClient.MEMBER_FINISH_PROJECT, Emitter.Listener {
            Log.d(TAG, "[socket] memberFinishProject: It's enough. Go to finish")

            //최종 정리뷰로 이동
            startDetailActivity()
        })
        Log.d(TAG, "[socket] memberFinishProject: set")
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

                            //새로운 roundIdx 초기화
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

    override fun onBackPressed() {
        showExitDialog()
    }
}
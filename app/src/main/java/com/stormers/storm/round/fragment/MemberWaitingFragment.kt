package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.network.RequestRound
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_setting_waiting_member) {

    private lateinit var activityButton: StormButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityButton = (activity as BaseProjectWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            visibility = View.GONE
        }

        //[socket] 라운드 설정이 마쳐지는지 확인
        waitingRoundSetting()

        //Todo: 소켓으로 라운드가 시작되는지 확인
        //Todo: 라운드가 시작되면 RoundProgressActivity로 전환
    }

    //라운드 정보를 받고 나면 실행될 콜백
    override fun afterGettingRoundInfo(roundIdx: Int) {
        //라운드 참여
        enterRound(roundIdx)

        view?.findViewById<TextView>(R.id.textview_round_ready)?.visibility = View.GONE
        view?.findViewById<LottieAnimationView>(R.id.lottieAnimationView)?.visibility = View.GONE
        view?.findViewById<TextView>(R.id.textview_readydone)?.visibility = View.VISIBLE
    }

    private fun waitingRoundSetting() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("joinRoom", preference.getProjectCode()!!)
        SocketClient.responseEvent("roundComplete", Emitter.Listener {
            Log.d("socketText", "호스트 측에서 라운드 설정을 완료하였습니다.")

            //라운드 정보 불러오기
            getRoundInfo()
        })
    }

    //라운드 참여
    private fun enterRound(roundIdx: Int){
        RetrofitClient.create(RequestRound::class.java).interfaceRoundEnter((RoundEnterModel(preference.getUserIdx()!!, roundIdx)))
            .enqueue(object  : Callback<SimpleResponse> {

                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    Log.d("라운드 참여 통신 실패", "${t}")
                }

                override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                    Log.d("멤버 라운드 참여 성공", "user : ${preference.getUserIdx()}, round : $roundIdx")

                    //라운드의 유저 정보 띄우기
                    showRoundUserLIst(roundIdx)

                    waitingRoundStart()
                }
            })
    }

    private fun waitingRoundStart() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.responseEvent("roundStartMember", Emitter.Listener {
            Log.d("startRound_socket", "START ROUND!!!")

            startRound()
        })
    }
}
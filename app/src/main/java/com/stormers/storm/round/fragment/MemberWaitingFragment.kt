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
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.model.RoundEntity
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.MemberRoundWaitingActivity
import com.stormers.storm.user.UserModel
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_setting_waiting_member) {

    private lateinit var activityButton: StormButton

    private val roundIdx = GlobalApplication.currentRound!!.roundIdx

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityButton = (activity as MemberRoundWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            visibility = View.GONE
        }

        //라운드 정보 불러오기
        getRoundInfo()
    }

    //라운드 참여
    private fun enterRound(roundIdx: Int){
        Log.d(TAG, "enterRound: userIdx: ${GlobalApplication.userIdx}")
        Log.d(TAG, "enterRound: projectIdx: ${GlobalApplication.currentProject!!.projectIdx}")
        Log.d(TAG, "enterRound: roundIdx: $roundIdx")

        RetrofitClient.create(RequestRound::class.java).interfaceRoundEnter((RoundEnterModel(preference.getUserIdx()!!, roundIdx)))
            .enqueue(object  : Callback<SimpleResponse> {

                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    Log.d(TAG, "enterRound: fail, ${t.message}")
                }

                override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "enterRound: success")

                        //라운드 시작을 기다림
                        waitingRoundStart()
                    } else {
                        Log.d(TAG, "enterRound: fail")
                    }
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

    private fun getRoundInfo(){

        RetrofitClient.create(RequestRound::class.java).responseRoundInfo(GlobalApplication.currentRound!!.roundIdx).enqueue(object : Callback<ResponseRoundInfoModel>{
            override fun onFailure(call: Call<ResponseRoundInfoModel>, t: Throwable) {
                Log.d(TAG, "getRoundInfo: Fail, ${t.message}")
            }
            override fun onResponse(call: Call<ResponseRoundInfoModel>, response: Response<ResponseRoundInfoModel>) {

                if(response.isSuccessful){

                    if(response.body()!!.success){

                        response.body()!!.data.let {
                            Log.d(TAG, "getRoundInfo: Success, roundNumber: ${it.roundNumber}, roundPurpose: ${it.roundPurpose}, roundTime: ${it.roundTime}")

                            //받은 라운드 정보를 앱 전역에 저장
                            GlobalApplication.currentRound = RoundModel(roundIdx, it.roundNumber, it.roundPurpose, it.roundTime, null)

                            //라운드 정보를 뷰에 초기화
                            initRoundInfo(it.roundPurpose, it.roundTime)

                            //라운드에 참여
                            enterRound(roundIdx)

                            //참여자 목록 갱신
                            refreshParticipants(roundIdx)
                        }
                    } else {
                        Log.d(TAG, "getRoundInfo: Not success, ${response.body()!!.message}")
                    }
                } else {
                    Log.d(TAG, "getRoundInfo: Not successful, ${response.message()}")
                }
            }
        })
    }
}
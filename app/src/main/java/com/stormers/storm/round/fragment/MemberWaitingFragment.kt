package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.BaseResponse
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.network.InterfaceRoundEnter
import com.stormers.storm.round.network.InterfaceRoundUser
import com.stormers.storm.user.ParticipantAdapter
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_setting_waiting_member) {

    private lateinit var activityButton: StormButton
    private lateinit var retrofitClient : InterfaceRoundUser

    private lateinit var recentRoundUserAdapter: ParticipantAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityButton = (activity as BaseProjectWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            visibility = View.GONE
        }

        //Todo: 소켓으로 라운드 설정이 마쳐지는지 확인
        //Todo: 라운드 설정이 마쳐지면 GET 라운드 정보 (roundIdx, roundPurpose, roundTime 등) //getRoundInfo() done
        //Todo: 라운드 정보를 사용하여 POST 라운드 참여 done
        //Todo: 참가한 사용자 목록 GET done
        //Todo: 소켓으로 라운드가 시작되는지 확인
        //Todo: 라운드가 시작되면 RoundProgressActivity로 전환

    }

    //라운드 정보를 받고 나면 실행될 콜백
    override fun afterGettingRoundInfo(roundIdx: Int) {
        //라운드 참여
        enterRound(roundIdx)
    }

    fun getUserList(){

        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("joinRoom", "roomCode")
        SocketClient.sendEvent("roundSetting",  "roomCode")

        SocketClient.responseEvent("roundcomplete", Emitter.Listener {
            Log.d("소켓 성공", it.toString())

            //showUserList()
        })
    }

    //라운드 참여
    private fun enterRound(roundIdx: Int){
        RetrofitClient.create(InterfaceRoundEnter::class.java).interfaceRoundEnter((RoundEnterModel(preference.getUserIdx()!!, roundIdx)))
            .enqueue(object  : Callback<BaseResponse> {

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Log.d("라운드 참여 통신 실패", "${t}")
                }

                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    Log.d("멤버 라운드 참여 성공", "user : ${preference.getUserIdx()}, round : $roundIdx")

                    //라운드의 유저 정보 띄우기
                    showRoundUserLIst(roundIdx)

                    //Todo: 라운드기 시작되는지 소켓 통신
                }
            })
    }
}
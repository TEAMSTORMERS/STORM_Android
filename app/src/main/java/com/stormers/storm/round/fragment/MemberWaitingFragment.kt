package com.stormers.storm.round.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.HostRoundSettingActivity
import com.stormers.storm.ui.MemberRoundWaitingActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_memberwaiting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder


class MemberWaitingFragment : BaseWaitingFragment(R.layout.fragment_memberwaiting) {

    private lateinit var activityButton: StormButton

    private val roundIdx = GlobalApplication.currentRound!!.roundIdx

    private var mActivity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MemberRoundWaitingActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityButton = (mActivity as MemberRoundWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            visibility = View.GONE
        }

        //라운드 정보 불러오기
        getRoundInfo()

        //라운드 시작을 기다림
        waitingRoundStart()
    }

    override fun onRoundStart() {
        super.onRoundStart()
        SocketClient.offEvent(SocketClient.ROUND_START_MEMBER)
    }

    override fun beHost() {
        super.beHost()
        //호스트의 라운드 시작을 기다리던 소켓을 해제
        SocketClient.offEvent(SocketClient.ROUND_START_MEMBER)

        //호스트를 기록
        GlobalApplication.isHost = true

        Log.d(TAG, "be host: Member is prompted")

        val intent = Intent(mActivity, HostRoundSettingActivity::class.java)

        intent.putExtra("isPromotion", true)

        mActivity?.let {
            it.startActivity(intent)
            it.finish()
        } ?: Log.e(TAG, "beHost: mActivity is null.")
    }

    private fun waitingRoundStart() {
        SocketClient.responseEvent(SocketClient.ROUND_START_MEMBER, Emitter.Listener {
            Log.d(TAG, "[socekt] roundStartMember: START ROUND!!!")

            startRound()
        })
        Log.d(TAG, "[socket] roundStartMember: set")
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
                            initRoundInfo(it.roundPurpose, it.roundTime, it.roundNumber)
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

    private fun initRoundInfo(roundPurpose: String, roundTime: Int, roundNumber: Int) {
        textview_memberwaiting_roundpurpose.text = StringBuilder("총 ").append(roundTime).append("분 예정").toString()
        textview_memberwaiting_roundtime.text = roundPurpose
        textview_memberwaiting_roundnumber.text = StringBuilder("Round ").append(roundNumber).toString()
    }
}
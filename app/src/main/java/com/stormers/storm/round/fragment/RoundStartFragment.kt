package com.stormers.storm.round.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.network.InterfaceRoundInfo
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.model.ResponseRoundInfoModel
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.ui.HostRoundWaitingActivity
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.ui.RoundSettingActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.*
import kotlinx.android.synthetic.main.fragment_round_start.view.*
import kotlinx.android.synthetic.main.fragment_round_start.view.textview_round_no
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.*
import kotlinx.android.synthetic.main.fragment_round_start.view.textview_round_no
import java.lang.StringBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Todo: ROUND정보 서버에서 받아오는 기능 수정해야함

class RoundStartFragment : BaseWaitingFragment(R.layout.fragment_round_start) {

    private lateinit var activityButton: StormButton

    private lateinit var dialog: StormDialog

    private lateinit var retrofitClient: InterfaceRoundInfo

    private var isNewRound = false

    private val projectIdx = preference.getProjectIdx()!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        isNewRound = arguments?.getBoolean("newRound")?: false

        dialog = StormDialogBuilder(StormDialogBuilder.LOADING_LOGO, "5초 후 라운드가 시작합니다").build()

        initActivityButton()

        initParticipant(view)

        //sendRoundInfo()

        getRoundInfo()
    }

    private fun initParticipant(view: View) {

        val round = StringBuilder()
        round.append("ROUND ")
            .append(preference.getRoundCount()!!)

        view.textview_round_no.text = round.toString()

        getRoundInfo()
    }

    private fun initActivityButton() {

        activityButton = if (isNewRound) {
            (activity as RoundSettingActivity).stormButton_ok_host_round_setting
        } else {
            (activity as HostRoundWaitingActivity).stormButton_ok_host_round_setting
        }

        activityButton.setOnClickListener {
            fragmentManager?.let { it1 -> dialog.show(it1, "round_start") }

            val handler = Handler()
            val handlerTask = Runnable {
                startActivity(Intent(activity, RoundProgressActivity::class.java))
            }

            handler.postDelayed(handlerTask, 5000)
        }
    }

    fun getRoundInfo(){
        retrofitClient = RetrofitClient.create(InterfaceRoundInfo::class.java)

        retrofitClient.responseRoundInfo(preference.getProjectIdx()!!).enqueue(object : Callback<ResponseRoundInfoModel>{
            override fun onFailure(call: Call<ResponseRoundInfoModel>, t: Throwable) {
                Log.d("RoundInfo 통신실패", "{$t}")
            }
            override fun onResponse(
                call: Call<ResponseRoundInfoModel>,
                response: Response<ResponseRoundInfoModel>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        Log.d("RoundInfo 통신성공","성공")

                        val time = StringBuilder()
                        time.append("총 ")
                            .append(response.body()!!.data.roundTime)
                            .append("분 예정")

                        round_time.text = time.toString()
                        round_subject.text = response.body()!!.data.roundPurpose

                    }
                }
            }
        })
    }

    fun sendRoundInfo(){

        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendStringEvent("joinRoom", "roomCode")

        SocketClient.responseEvent("roomState", Emitter.Listener{
            Log.d("test", "test2")
        })


//        SocketClient.responseEvent("roundcomplete", Emitter.Listener {
//            Log.d("이게 통신이다 임마", it.toString())
//            Log.d("이게 통신이다 임마", it[0].toString())
//        })


        goToFragment(RoundStartFragment::class.java,null)
    }
}
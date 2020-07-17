package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton

import com.stormers.storm.card.network.InterfaceRoundCount
import com.stormers.storm.network.*
import com.stormers.storm.round.model.ResponseRoundCountModel
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.model.RoundSettingModel
import com.stormers.storm.round.network.InterfaceRoundEnter
import kotlinx.android.synthetic.main.activity_round_setting.*
import com.stormers.storm.ui.RoundSettingActivity
import com.stormers.storm.util.substringForMinute
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class   HostRoundSettingFragment : BaseFragment(R.layout.fragment_host_round_setting) {

    private lateinit var timePickerDialog: StormDialog

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var activityButton: StormButton

    private lateinit var retrofitClient: InterfaceRoundCount

    private val projectIdx = preference.getProjectIdx()

    private val userIdx = preference.getUserIdx()

    private val roundIdx = preference.getRoundIdx()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //서버로부터 라운드 정보를 가져옴
        getRoundCount()

        initActivityButton()

        initDialogButton()

        initDialog()

        textview_roundsetting_time.setOnClickListener {
            timePickerDialog.show(fragmentManager!!, "timepicker")
        }

    }

    private fun initDialogButton() {
        val button = StormDialogButton("입력", true, null)

        button.pickerListener = object : StormDialogButton.OnPickerClickListener {
            override fun onClick(minute: Int) {
                val string = StringBuilder()
                string.append(minute)
                    .append(" 분")
                textview_roundsetting_time.text = string.toString()
            }
        }

        buttonArray.add(button)
    }

    private fun initDialog() {
        timePickerDialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "라운드 목표 시간을 선택해주세요")
            .setContentRes(R.layout.view_timepicker)
            .setButtonArray(buttonArray)
            .isPicker(true)
            .build()
    }

    private fun initActivityButton() {
        activityButton = (activity as RoundSettingActivity).stormButton_ok_host_round_setting

        activityButton.setText("확인")

        activityButton.setOnClickListener {
            Log.d("Round Setting","버튼눌림" )
            if (textview_round_goal.text.isNullOrBlank() || textview_roundsetting_time.text.isNullOrBlank()) {
                Toast.makeText(context, "라운드 목표 혹은 라운드 소요시간을 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else {
                projectIdx?.let {
                    RetrofitClient.create(InterfaceRoundSetting::class.java).roundSetting(
                        RoundSettingModel(
                            it,
                            textview_round_goal.text.toString(),
                            textview_roundsetting_time.text.toString().substringForMinute()
                        )
                    ).enqueue(
                        object : Callback<BaseResponse> {
                            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                                Log.d("Round Setting 통신 실패", "${t}")
                            }

                            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                                if (response.isSuccessful) {
                                    if (response.body()!!.success) {
                                        Log.d("Round Setting 통신 성공", response.body()!!.message)

                                        //RoundIdx 저장
                                        preference.setRoundIdx(response.body()!!.data)

                                        //라운드 입장
                                        enterRound(response.body()!!.data)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    fun getRoundCount() {
        retrofitClient = RetrofitClient.create(InterfaceRoundCount::class.java)

        projectIdx?.let {
            retrofitClient.responseRoundCount(it)
                .enqueue(object : Callback<ResponseRoundCountModel> {
                    override fun onFailure(call: Call<ResponseRoundCountModel>, t: Throwable) {
                        Log.d("RoundCount 통신실패", "${t}")
                    }

                    override fun onResponse(call: Call<ResponseRoundCountModel>, response: Response<ResponseRoundCountModel>) {
                        if (response.isSuccessful) {
                            if (response.body()!!.success) {
                                Log.d("RoundCount 통신 성공", "성공")

                                preference.setRoundCount(response.body()!!.data)

                                val round = StringBuilder()
                                round.append("ROUND ")
                                    .append(response.body()!!.data)
                                textview_roundnumber.text = round.toString()
                            }
                        }
                    }
                })
        }
    }
    fun enterRound(roundIdx: Int){
        RetrofitClient.create(InterfaceRoundEnter::class.java).interfaceRoundEnter((RoundEnterModel(userIdx!!, roundIdx)))
            .enqueue(object  : Callback<SimpleResponse> {

            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d("enterRound", "failed : ${t.message}")
                Log.d("enterRound", "userIdx: $userIdx")
                Log.d("enterRound", "projectIdx: ${preference.getProjectIdx()}")
                Log.d("enterRound", "roundIdx: $roundIdx")
            }

            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                Log.d("enterRound", "${response.message() } $response" )
                Log.d("enterRound", "userIdx: $userIdx")
                Log.d("enterRound", "projectIdx: ${preference.getProjectIdx()}")
                Log.d("enterRound", "roundIdx: $roundIdx")

                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        //소켓으로 방에 참가하기
                        joinRoundRoom()
                    }
                }
            }
        })
    }

    fun joinRoundRoom(){

        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("joinRoom", "roomCode")
        SocketClient.sendEvent("roundSetting",  "roomCode")

        SocketClient.responseEvent("roundComplete", Emitter.Listener {
            Log.d("SocketJoinRoom", "Success.")

            //방에 들어갔으면 프래그먼트 전환
            goToFragment(RoundStartFragment::class.java, null)
        })
    }
}
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

import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.network.*
import com.stormers.storm.round.network.response.ResponseRoundCountModel
import com.stormers.storm.round.model.RoundEnterModel
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.round.model.RoundSettingModel
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.activity_round_setting.*
import com.stormers.storm.ui.RoundSettingActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class HostRoundSettingFragment : BaseFragment(R.layout.fragment_host_round_setting) {

    private lateinit var timePickerDialog: StormDialog

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var activityButton: StormButton

    private lateinit var retrofitClient: RequestRound

    private val projectIdx = GlobalApplication.currentProject!!.projectIdx

    private val userIdx = GlobalApplication.userIdx

    private var roundCount: Int? = null

    private var roundTime: Int? = null

    private var roundPurpose: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //서버로부터 라운드 횟수를 가져옴
        getRoundCount()

        //액비티이단의 확인 버튼 초기화
        initActivityButton()

        //라운드 목표 시간 다이어그램 버튼 초기화
        initDialogButton()

        //라운드 목표 시간 다이어그램 초기화
        initDialog()

        //라운드 목표 시간 버튼 초기화
        textview_roundsetting_time.setOnClickListener {
            timePickerDialog.show(fragmentManager!!, "timepicker")
        }

    }

    private fun initDialogButton() {
        val button = StormDialogButton("입력", true, null)

        button.pickerListener = object : StormDialogButton.OnPickerClickListener {
            override fun onClick(minute: Int) {
                roundTime = minute
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
            createRound()
        }
    }

    private fun createRound() {
        roundPurpose = textview_round_goal.text.toString()

        if (roundPurpose.isNullOrBlank() || roundTime == null) {
            Toast.makeText(context, "라운드 목표 혹은 라운드 소요시간을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            RetrofitClient
                .create(RequestRound::class.java)
                .roundSetting(RoundSettingModel(projectIdx, roundPurpose!!, roundTime!!))
                .enqueue(object : Callback<BaseResponse> {

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.d(TAG, "createRound: fail, ${t.message}")
                    }

                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                        if (response.isSuccessful) {
                            if (response.body()!!.success) {
                                val roundIdx = response.body()!!.data
                                Log.d(TAG, "createRound: success, roundIdx: $roundIdx")

                                //현재 라운드 앱 전역에 저장
                                GlobalApplication.currentRound = RoundModel(roundIdx, roundCount, roundPurpose, roundTime, null)

                                //라운드 입장
                                enterRound(roundIdx)
                            } else {
                                Log.d(TAG, "createRound: not success, ${response.body()!!.message}")
                            }
                        } else {
                            Log.d(TAG, "createRound: not success, ${response.body()!!.message}")
                        }
                    }
                }
                )
        }
    }

    private fun getRoundCount() {
        retrofitClient = RetrofitClient.create(RequestRound::class.java)

        retrofitClient.responseRoundCount(projectIdx)
            .enqueue(object : Callback<ResponseRoundCountModel> {
                override fun onFailure(call: Call<ResponseRoundCountModel>, t: Throwable) {
                    Log.d(TAG, "getRoundCount: fail, ${t.message}")
                }

                override fun onResponse(call: Call<ResponseRoundCountModel>, response: Response<ResponseRoundCountModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "getRoundCount : success, ${response.body()!!.data}")

                            preference.setRoundCount(response.body()!!.data)
                            setRoundCount(response.body()!!.data)
                        } else {
                            Log.d(TAG, "getRoundCount : not success, ${response.message()}")
                        }
                    } else {
                        Log.d(TAG, "getRoundCount : not success, ${response.message()}")
                    }
                }
            })

    }

    private fun setRoundCount(roundCount: Int) {
        this.roundCount = roundCount
        val round = StringBuilder()
        round.append("ROUND ")
            .append(roundCount)
        textview_roundnumber.text = round.toString()
    }

    fun enterRound(roundIdx: Int){
        Log.d(TAG, "enterRound: userIdx: $userIdx")
        Log.d(TAG, "enterRound: projectIdx: ${GlobalApplication.currentProject!!.projectIdx}")
        Log.d(TAG, "enterRound: roundIdx: $roundIdx")

        RetrofitClient.create(RequestRound::class.java).interfaceRoundEnter((RoundEnterModel(userIdx, roundIdx)))
            .enqueue(object  : Callback<SimpleResponse> {

            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d(TAG, "enterRound: failed, ${t.message}")
            }

            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d(TAG, "enterRound: success")

                        //소켓으로 방에 참가하기
                        joinRoundRoom()
                    }
                    Log.d(TAG, "enterRound: not success")
                }
                Log.d(TAG, "enterRound: not success")
            }
        })
    }

    fun joinRoundRoom(){

        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("joinRoom", GlobalApplication.currentProject!!.projectCode!!)
        SocketClient.sendEvent("roundSetting", GlobalApplication.currentProject!!.projectCode!!)

        SocketClient.responseEvent("roundComplete", Emitter.Listener {
            Log.d("SocketJoinRoom", "Success.")

            //방에 들어갔으면 프래그먼트 전환
            goToFragment(HostRoundWaitingFragment::class.java, null)
        })
    }
}
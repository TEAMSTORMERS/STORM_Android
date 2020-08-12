package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.stormers.storm.R
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import kotlinx.android.synthetic.main.activity_round_progress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class RoundProgressActivity : BaseActivity() {

    private lateinit var retrofitClient: RequestRound

    private var projectIdx = -1
    private var roundIdx = -1

    val projectName = preference.getProjectName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        //Debug 용도로 라운드 목표를 터치하면 라운드가 종료되도록 함
        this.textView_round_goal.setOnClickListener {
            startActivity(Intent(this, RoundFinishActivity::class.java))
            finish()
        }

        projectIdx = preference.getProjectIdx()?: -1
        roundIdx = preference.getRoundIdx()?: -1

        retrofitClient = RetrofitClient.create(RequestRound::class.java)

        retrofitClient.responseRoundInfo(projectIdx).enqueue(object :
            Callback<ResponseRoundInfoModel> {
            override fun onFailure(call: Call<ResponseRoundInfoModel>, t: Throwable) {
                if (t.message != null){
                    Log.d("RoundProgressActivity", t.message!!)
                } else {
                    Log.d("RoundProgressActivity", "통신실패")
                }
            }

            override fun onResponse(
                call: Call<ResponseRoundInfoModel>,
                response: Response<ResponseRoundInfoModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("RoundProgressActivity", "받아온 라운드 정보 : ${response.body()!!.data}")
                        val roundNumber = StringBuilder()
                        roundNumber.append("ROUND ")
                            .append(response.body()!!.data.roundNumber).toString()

                        val roundTime : Long = ((response.body()!!.data.roundTime) * 1000 * 60).toLong()
                        countDown(roundTime)

                        textView_project_name.setText(projectName)
                        textView_round_goal.setText(response.body()!!.data.roundPurpose)
                        textView_round.setText(roundNumber)
                    }
                    else {
                        Log.d("RoundProgressActivity", "통신실패")
                        Log.d("RoundProgressActivity", "받아온 라운드 정보 : ${response.body()!!.message}")
                    }
                } else {
                    Log.d("RoundProgressActivity", "${response.message()} , ${response.errorBody()}")
                }
            }
        })

        goToFragment(AddCardFragment::class.java, null)

    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }

    private fun countDown(time:Long) {
        val countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                val millis: Long = p0
                val hms = String.format(
                    "총 %02d:%02d 남음", //(p0/(1000*60)%60), ((p0/1000)%60)
                    (TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
                    (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    ))
                )
                textView_time.setText(hms)
            }

            override fun onFinish() {
                startActivity(Intent(this@RoundProgressActivity, RoundFinishActivity::class.java))
                finish()
            }
        }
        countDownTimer.start()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments[0] is AddCardFragment) {
            val buttonList = ArrayList<StormDialogButton>()
            buttonList.add(StormDialogButton("취소", true, null))
            buttonList.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    //Todo: 프로젝트 나가기
                    finish()
                }
            }))
            StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "프로젝트에서 나가시겠습니까?")
                .setHorizontalArray(buttonList)
                .build()
                .show(supportFragmentManager, "exit")
        } else {
            goToFragment(AddCardFragment::class.java, null)
        }
    }
}
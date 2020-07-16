package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.card.fragment.RoundmeetingFragment
import com.stormers.storm.network.InterfaceRoundInfo
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.model.ResponseRoundInfoModel
import com.stormers.storm.round.network.FinalRoundInterface
import com.stormers.storm.round.network.ResponseFinalRoundData
import kotlinx.android.synthetic.main.activity_project_cardlist.*
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class RoundProgressActivity : BaseActivity() {

    private lateinit var retrofitClient: InterfaceRoundInfo

    private var projectIdx = -1
    private var roundIdx = -1

    val projectName = preference.getProjectName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        setSupportActionBar(include_roundprogress_toolbar.toolbar)

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.host_a_1_btn_back)
        }

        //Debug 용도로 라운드 목표를 터치하면 라운드가 종료되도록 함
        this.textView_round_goal.setOnClickListener {
            startActivity(Intent(this, RoundFinishActivity::class.java))
        }

        projectIdx = preference.getProjectIdx()!!
        roundIdx = preference.getRoundIdx()!!

        retrofitClient = RetrofitClient.create(InterfaceRoundInfo::class.java)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    //메뉴 선택시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            //Fixme: 마이페이지 아이콘이 작아지는 문제 해결
            R.id.menu_toolbar_mypage -> {
                //Todo: 마이페이지로 이동하는 코드
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                goToFragment(RoundmeetingFragment::class.java, null)
            }
        }
        countDownTimer.start()
    }
}
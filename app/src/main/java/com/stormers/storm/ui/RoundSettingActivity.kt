package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.BaseResponse
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.round.network.InterfaceRoundExit
import kotlinx.android.synthetic.main.activity_round_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundSettingActivity : BaseActivity() {

    private var projectIdx = -1
    private var userIdx = -1
    private var roundIdx = -1

    private lateinit var retrofitClient: InterfaceRoundExit

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        Log.d("test", "test2")

        textview_projectcard_title.setOnClickListener {
            Log.d("버튼 눌림", "눌림")
            roundExit()
        }


        toolbar_exit.setOnClickListener {
            Log.d("버튼 눌림", "눌림")
            roundExit()
        }

        projectIdx = preference.getProjectIdx()!!
        userIdx = preference.getUserId()!!
        roundIdx = preference.getRoundIdx()!!

        textview_projectcard_title.text = preference.getProjectName()

        // val toolbar_exit : ImageButton

        // toolbar_exit = findViewById(R.id.toolbar_exit) as ImageButton
        retrofitClient = RetrofitClient.create(InterfaceRoundExit::class.java)

        goToFragment(HostRoundSettingFragment::class.java, null)

    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }

    fun roundExit() {

        retrofitClient.roundExit(userIdx, roundIdx).enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("통신실패", "${t}")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful)
                    if (response.body()!!.success) {
                        Log.d("라운드 나가기 통신 성공", "성공")
                        val intent = Intent(this@RoundSettingActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
            }
        })
    }


}
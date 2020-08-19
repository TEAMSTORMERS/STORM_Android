package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.stormers.storm.R
import com.stormers.storm.UserWithdrawal.model.UserWithdrawalModel
import com.stormers.storm.UserWithdrawal.network.RequestWithdrawal
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.Response
import com.stormers.storm.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_mypage_withdrawal.*
import kotlinx.android.synthetic.main.activity_mypage_withdrawal_check.*
import retrofit2.Call
import retrofit2.Callback

class MypageWithdrawalCheckActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_withdrawal_check)

        stormtoolbar_mypage_withdrawal_check.setBackButton()

        CompleteWithdrawal()
    }

    fun CompleteWithdrawal() {

        stormbutton_withdrawal_check.setOnClickListener {
            RetrofitClient.create(RequestWithdrawal::class.java)
                .requestWithdrawal(
                    UserWithdrawalModel(
                        preference.getUserIdx()!!,
                        intent.getStringExtra("userPassword"),
                        intent.getStringExtra("reason")!!
                    )
                ).enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("통신실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if(response.isSuccessful){
                            if(response.body()!!.success){

                                val intent = Intent(this@MypageWithdrawalCheckActivity, LoginActivity::class.java)
                                preference.setAutoLogIn(false)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                Log.d("탈퇴실패", response.message())
                            }
                        } else {
                            Log.d("탈퇴실패", response.message())
                        }
                    }
                })

        }

    }
}
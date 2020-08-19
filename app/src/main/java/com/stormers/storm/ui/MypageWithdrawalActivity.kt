package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.stormers.storm.R
import com.stormers.storm.UserWithdrawal.RequestPasswordCheck
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.Response
import com.stormers.storm.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_mypage_withdrawal.*
import retrofit2.Call
import retrofit2.Callback

class MypageWithdrawalActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_withdrawal)

        stormbutton_withdrawal_next.setOnClickListener {
            val nextIntent = Intent(this, MypageWithdrawalCheckActivity::class.java)
            startActivity(nextIntent)
        }

        stormtoolbar_mypage_withdrawal.setBackButton()

        checkPassword()

    }

    fun checkPassword() {

        stormbutton_withdrawal_next.setOnClickListener{

            RetrofitClient.create(RequestPasswordCheck::class.java)
                .requestPasswordCheck(
                    GlobalApplication.userIdx,
                    edittext_withdrawal_pwd.text.toString()
                )
                .enqueue(object : Callback<Response>{
                    override fun onFailure(call: Call<Response>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if (response.isSuccessful){
                           if (response.body()!!.success){
                               Log.d("비밀번호 일치",response.body()!!.message)
                           }
                        }
                    }
                })
        }

    }

}
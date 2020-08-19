package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.UserWithdrawal.model.PasswordCheckModel
import com.stormers.storm.UserWithdrawal.model.UserWithdrawalModel
import com.stormers.storm.UserWithdrawal.network.RequestPasswordCheck
import com.stormers.storm.UserWithdrawal.network.RequestWithdrawal
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

        Log.d("userIdx", preference.getUserIdx().toString())

        checkPassword()

    }

    fun checkPassword() {

        stormbutton_withdrawal_next.setOnClickListener{

            RetrofitClient.create(RequestPasswordCheck::class.java)
                .requestPasswordCheck(
                    PasswordCheckModel(
                        preference.getUserIdx()!!,
                        edittext_withdrawal_pwd.text.toString()
                    )
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
                               textview_withdrawal_pwd_notice.visibility = View.GONE

                               val intent = Intent(this@MypageWithdrawalActivity,MypageWithdrawalCheckActivity::class.java)
                               intent.putExtra("userPassword",edittext_withdrawal_pwd.text.toString())
                               intent.putExtra("reason",edittext_withdrawal_reason.text.toString()!!)
                               startActivity(intent)
                           }
                        } else {
                            Log.d("비밀번호 불일치", response.errorBody().toString())
                            textview_withdrawal_pwd_notice.visibility = View.VISIBLE
                        }
                    }
                })
        }
    }
}
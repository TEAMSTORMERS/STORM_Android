package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imagebutton_login_kakao.setOnClickListener {
            //Todo: 카카오 로그인 기능

            //Debug 용도로 일단 메인화면으로 이동하게 함
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            this@LoginActivity.finish()
        }
    }
}
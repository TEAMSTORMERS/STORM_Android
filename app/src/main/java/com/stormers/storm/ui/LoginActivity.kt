package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(preference.getAutoLogIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //애니메이션 초기화
        initAnimation()

        //뷰 초기화
        initView()
    }

    private fun initView() {
        button_login.setOnClickListener{
            if (edittext_email_login.text.isNullOrBlank() || edittext_password_login.text.isNullOrBlank()){
                textview_login_info.visibility = View.VISIBLE

            } else {
                if(checkbox_auto_login.isChecked){
                    preference.setAutoLogIn(true)
                } else {
                    preference.setAutoLogIn(false)
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        textview_goto_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    //Lottie 애니메이션 로그인뷰
    private fun initAnimation() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login)
        animationView.setAnimation("login_bg.json")
        animationView.repeatCount = INFINITE
        animationView.playAnimation()
    }
}

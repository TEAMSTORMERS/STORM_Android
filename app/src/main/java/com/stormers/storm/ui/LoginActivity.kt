package com.stormers.storm.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.kakao.auth.Session
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.util.SharedPreference
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        gotoSignUp()
        logIn()
        autoLogIn()

    }

    //Lottie 애니메이션 로그인뷰
    private fun initView() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login)
        animationView.setAnimation("login_0816.json")
        animationView.repeatCount = INFINITE
        animationView.playAnimation()
    }

    fun logIn(){

        button_login.setOnClickListener{
            if (edittext_email_login.text.isNullOrBlank() || edittext_password_login.text.isNullOrBlank()){
                textview_login_info.visibility = View.VISIBLE
            } else {
                textview_login_info.visibility = View.GONE

                startActivity(Intent(this, MainActivity::class.java))

                if(checkbox_auto_login.isChecked){
                    preference.setAutoLogIn(true)
                } else {
                    preference.setAutoLogIn(false)
                }

                finish()
            }
        }
    }

    fun gotoSignUp() {

        textview_goto_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    fun autoLogIn() {

        val status_auto_login = preference.getAutoLogIn()

        if(status_auto_login == true){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        }
    }
}

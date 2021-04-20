package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.stormers.storm.logIn.RequestLogIn
import com.stormers.storm.logIn.model.LogInModel
import com.stormers.storm.logIn.model.response.ResponseLogIn
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.StormEditText
import com.stormers.storm.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mypage_withdrawal.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        scrollViewKeyBoard(scrollview_login)

        goToSignUp()

        autoLogIn()
        //애니메이션 초기화
        initAnimation()
    }

    private fun initView() {
        //전체 지우기 버튼 활성화/비활성화
        edittext_email_login.setEditTextWatcher(null, null, true)
        edittext_password_login.setEditTextWatcher(null, null, true)

        button_login.setOnClickListener{
            if (edittext_email_login.text.isNullOrBlank() || edittext_password_login.text.isNullOrBlank()){
                textview_login_info.visibility = View.VISIBLE
            } else {

                if(checkbox_auto_login.isChecked){
                    preference.setAutoLogIn(true)
                } else {
                    preference.setAutoLogIn(false)
                }

                showLoadingDialog()

                RetrofitClient.create(RequestLogIn::class.java).requestLogIn(
                    LogInModel(
                        edittext_email_login.text.toString(),
                        edittext_password_login.text.toString())
                ).enqueue(object :retrofit2.Callback<ResponseLogIn>{
                    override fun onFailure(call: Call<ResponseLogIn>, t: Throwable) {
                        dismissLoadingDialog()
                        Log.d("로그인 실패", "${t.message}")
                    }

                    override fun onResponse(
                        call: Call<ResponseLogIn>,
                        response: Response<ResponseLogIn>
                    ) {
                        dismissLoadingDialog()
                        if(response.isSuccessful){
                            if (response.body()!!.success){
                                Log.d("userIdx", response.body()!!.data.toString())
                                preference.setUserIdx(response.body()!!.data)

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.d("로그인 실패", response.message())
                            }
                        } else {
                            Log.d("로그인 실패", response.message())
                            textview_login_info.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
    }

    fun goToSignUp() {
        textview_goto_sign_up.setOnClickListener {
            val intent = Intent(this, SetEmailPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun autoLogIn() {

        if(preference.getAutoLogIn() == true && preference.getUserIdx() != null) {
            
                Log.d("userIdx", preference.getUserIdx().toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()

        } else {
            initView()
        }
    }

    //Lottie 애니메이션 로그인뷰
    private fun initAnimation() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login)
        animationView.setAnimation("login_0816.json")
        animationView.repeatCount = INFINITE
        animationView.playAnimation()
    }
}

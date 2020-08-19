package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.stormers.storm.R
import com.stormers.storm.SignUp.InterfaceSignUp
import com.stormers.storm.SignUp.ResponseSignUpModel
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.SignUpActivity.Companion.IS_DEFAULT_IMAGE
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.fragment_mypage_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetEmailPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        signUpTextWatcher()
        goBackActivity()
        checkVaildEmailType()
        goCompleteSignUpActivity()
    }

    fun signUpTextWatcher() {

        edittext_input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (edittext_input_password.text!!.length < 8){
                    textview_password_warning.visibility = View.VISIBLE
                    textview_password_warning.setText(R.string.input_password_more_char)
                    button_next_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
                    button_next_signup.isEnabled = false

                } else {

                    if (!edittext_input_password.text.toString().equals(edittext_password_check.text.toString())){
                        textview_password_warning.setText(R.string.check_password)
                        textview_password_warning.visibility = View.VISIBLE
                        button_next_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
                        button_next_signup.isEnabled = false
                    } else {
                        textview_password_warning.visibility = View.GONE
                        button_next_signup.setBackgroundResource(R.drawable.button_activated_signup)
                        button_next_signup.isEnabled = true
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        edittext_password_check.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if (edittext_password_check.text!!.length < 8){
                    textview_password_warning.visibility = View.VISIBLE
                    textview_password_warning.setText(R.string.input_password_more_char)
                    button_next_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
                    button_next_signup.isEnabled = false

                } else {
                    if (!edittext_password_check.text.toString().equals(edittext_input_password.text.toString())){
                        textview_password_warning.setText(R.string.check_password)
                        textview_password_warning.visibility = View.VISIBLE
                        button_next_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
                        button_next_signup.isEnabled = false
                    } else {
                        textview_password_warning.visibility = View.GONE
                        button_next_signup.setBackgroundResource(R.drawable.button_activated_signup)
                        button_next_signup.isEnabled = true
                    }

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun goBackActivity(){
        button_back_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    fun goCompleteSignUpActivity() {

        button_next_signup.setOnClickListener{
            val fileUserImage = BitmapConverter.bitmapToFile(GlobalApplication.profileBitmap!! , this.cacheDir.toString())

            val requestUserImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileUserImage!!)

            val sendUserImage = MultipartBody.Part.createFormData("user_img", fileUserImage.name, requestUserImageFile)

            val userName = RequestBody.create(MediaType.parse("text/plain"), intent.getStringExtra("userName"))

            val userEmail = RequestBody.create(MediaType.parse("text/plain"), edittext_input_email.text.toString())

            val userPassword = RequestBody.create(MediaType.parse("text/plain"), edittext_input_password.text.toString())

            val userImageFlag = RequestBody.create(MediaType.parse("text/plain"), intent.getIntExtra("userImageFlag", IS_DEFAULT_IMAGE).toString())
            
            RetrofitClient.create(InterfaceSignUp::class.java).interfaceSignUp(sendUserImage, userName,userEmail,userPassword, userImageFlag)
                .enqueue(object : Callback<ResponseSignUpModel>{
                    override fun onFailure(call: Call<ResponseSignUpModel>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<ResponseSignUpModel>,
                        response: Response<ResponseSignUpModel>
                    ) {
                        if(response.isSuccessful){
                            if (response.body()!!.success){

                                val intent = Intent(this@SetEmailPasswordActivity, CompleteSignUpActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                if(response.body()!!.status == 400) {
                                    Log.d("정보누락", response.body()!!.status.toString())
                                } else {
                                    if (response.body()!!.status == 600){
                                        Log.d("중복이메일, DB오류", response.body()!!.status.toString())
                                        textview_email_warning.setText("이미 사용중인 이메일입니다.")
                                        textview_email_warning.visibility = View.VISIBLE
                                    } else {
                                        Log.d("서버오류", response.body()!!.status.toString())
                                    }
                                }
                            }
                        } else {
                            Log.d("서버통신 오류", response.message())
                        }
                    }
                })
        }
    }

    fun checkVaildEmailType() {

        edittext_input_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if(isEmailValid(edittext_input_email.text.toString()) == false){
                    textview_email_warning.visibility = View.VISIBLE
                } else {
                    textview_email_warning.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
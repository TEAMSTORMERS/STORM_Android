package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View

import com.stormers.storm.R
import com.stormers.storm.SignUp.InterfaceSignUp
import com.stormers.storm.SignUp.ResponseSignUpModel
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_set_email_password.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetEmailPasswordActivity : BaseActivity() {

    lateinit var UserImageFlag : RequestBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        UserImageFlag = RequestBody.create(MediaType.parse("text/plain"), intent.getStringExtra("UserImageFlag"))


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

    private fun goCompleteSignUpActivity() {

        button_next_signup.setOnClickListener{
            Log.d("버튼눌림", " 버튼눌림")

            val fileUserImage = BitmapConverter.bitmapToFile(GlobalApplication.profileBitmap!! , this.cacheDir.toString())

            val requestUserImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileUserImage!!)

            val sendUserImage = MultipartBody.Part.createFormData("userName", fileUserImage.name, requestUserImageFile)

            val userName = RequestBody.create(MediaType.parse("text/plain"), intent.getStringExtra("userName"))

            val userEmail = RequestBody.create(MediaType.parse("text/plain"), edittext_input_email.text.toString())

            val userPassword = RequestBody.create(MediaType.parse("text/plain"), edittext_input_password.text.toString())

            //Todo : HTTP통신 수정

            RetrofitClient.create(InterfaceSignUp::class.java).interfaceSignUp(
                sendUserImage, userName, userEmail, userPassword, UserImageFlag)
                .enqueue(object : Callback<ResponseSignUpModel>{
                    override fun onFailure(call: Call<ResponseSignUpModel>, t: Throwable) {
                        if (t.message != null){
                            Log.d("회원가입실패", t.message!!)
                        } else {
                            Log.d("통신실패", "통신실패")
                        }
                    }

                    override fun onResponse(
                        call: Call<ResponseSignUpModel>,
                        response: Response<ResponseSignUpModel>
                    ) {
                        if(response.isSuccessful){
                            if (response.body()!!.success ){
                                Log.d("회원가입 성공", response.body()!!.success.toString())

                                val intent = Intent(this@SetEmailPasswordActivity, CompleteSignUpActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                textview_email_warning.visibility = View.VISIBLE
                            }
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
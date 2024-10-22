package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import com.stormers.storm.R
import com.stormers.storm.SignUp.CheckEmailDuplicationModel
import com.stormers.storm.SignUp.InterfaceSignUp
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.StormEditText
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.util.ChromeTabHelper
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.activity_set_email_password.button_back_signup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetEmailPasswordActivity : BaseActivity() {

    val buttonArray = ArrayList<StormDialogButton>()

    var completePassword : Boolean = false

    var completeCheckInfo : Boolean = false

    var completeCheckEmail : Boolean = false

    var checkedServiceLagacy : Boolean = false

    var checkedPersonalInformaiton : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        scrollViewKeyBoard(scrollview_signup_email)

        checkVaildEmailType()
        checkPasswordTextWatcher()
        isCheckedLegacy()

        goSignUpActivity()
        goBackActivity()
        checkPersonalInfoLegacy()

    }

    private fun goBackActivity(){
        button_back_signup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun goSignUpActivity(){
        button_next_signup.setOnClickListener{

            RetrofitClient.create(InterfaceSignUp::class.java).requestCheckEmailDuplication(
                CheckEmailDuplicationModel(
                    edittext_input_email.text.toString()
                )
            ).enqueue( object : Callback<SimpleResponse>{
                    override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                        Log.d("이메일중복확인 통신 실패", "${t}")
                    }

                    override fun onResponse(
                        call: Call<SimpleResponse>,
                        response: Response<SimpleResponse>
                    ) {
                        if (response.isSuccessful){
                            if (response.body()!!.success){

                                textview_email_warning.visibility = View.GONE

                                val intent = Intent(this@SetEmailPasswordActivity,SignUpActivity::class.java)
                                intent.putExtra("userEmail",edittext_input_email.text.toString())
                                intent.putExtra("userPassword",edittext_input_password.text.toString())
                                startActivity(intent)

                            } else {
                                Log.d("이메일중복체크 오류", response.errorBody().toString())
                            }
                        } else {
                            if(response.code() == 400){
                                textview_email_warning.setText(R.string.check_email_type)
                                textview_email_warning.visibility = View.VISIBLE
                            } else {
                                if (response.code() == 600){
                                    textview_email_warning.setText(R.string.email_already_used)
                                    textview_email_warning.visibility = View.VISIBLE
                                } else {
                                    Log.d("DB오류/서버오류", response.code().toString())
                                }
                            }

                        }
                    }
                }

            )
        }
    }


    private fun checkVaildEmailType() {

        edittext_input_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkEmailCondition(edittext_input_email)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun checkPasswordTextWatcher() {

        edittext_input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                checkPasswordCondition(edittext_input_password, edittext_password_check, textview_password_warning)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        edittext_password_check.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                checkPasswordCondition(edittext_password_check, edittext_input_password, textview_password_warning)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edittext_input_email.setEditTextWatcher(null, null, true)
        edittext_input_password.setEditTextWatcher(null, null, true)
        edittext_password_check.setEditTextWatcher(null, null, true)

    }

    fun checkPasswordCondition(editText1: StormEditText, editText2: StormEditText, textView: TextView) {

        if (editText1.text!!.length < 8){
            textView.visibility = View.VISIBLE
            textView.setText(R.string.input_password_more_char)

            completePassword = false

        } else {

            if (!editText1.text.toString().equals(editText2.text.toString())){
                textView.setText(R.string.check_password)
                textView.visibility = View.VISIBLE

                completePassword = false

            } else {
                textView.visibility = View.GONE

                completePassword = true
                Log.d("completePassword","true")
            }
        }
        checkAllCondition()
    }

    fun checkEmailCondition(editText: StormEditText) {

        if(!isEmailValid(editText.text.toString())){
            textview_email_warning.setText(R.string.check_email_type)
            textview_email_warning.visibility = View.VISIBLE
            completeCheckEmail = false
        } else {
            textview_email_warning.visibility = View.GONE
            completeCheckEmail = true
            Log.d("completeCheckEmail","true")
        }
        checkAllCondition()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isCheckedLegacy() {
        checkbox_service_lagacy.setOnClickListener {
            if (checkbox_service_lagacy.isChecked) {
                checkedServiceLagacy = true
                Log.d("checkbox_service_lagacy", "true")
            }
            else {
                checkedServiceLagacy = false
                Log.d("checkbox_service_lagacy", "false")
            }
            completeCheckInfo()
        }

        checkbox_personal_information.setOnClickListener {
            if (checkbox_personal_information.isChecked) {
                checkedPersonalInformaiton = true
                Log.d("checkbox_information", "true")
            }
            else {
                checkedPersonalInformaiton = false
                Log.d("checkbox_information", "false")
            }
            completeCheckInfo()
        }
    }

    private fun completeCheckInfo() {
        if (checkedServiceLagacy && checkedPersonalInformaiton) {
            completeCheckInfo = true
            Log.d("completeCheckInfo", "true")
        }
        else {
            completeCheckInfo = false
            Log.d("completeCheckInfo", "false")
        }
        checkAllCondition()
    }

    private fun unusableNextButton() {
        button_next_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
        button_next_signup.isEnabled = false

    }

    private fun usableNextButton() {
        button_next_signup.setBackgroundResource(R.drawable.button_activated_signup)
        button_next_signup.isEnabled = true

    }

    private fun checkAllCondition() {
        if(completeCheckEmail && completePassword && completeCheckInfo){
            usableNextButton()
        } else {
            unusableNextButton()
        }
    }

    //크롬이 설치되어 있으면 크롬으로 실행, 이 외에는 기본 웹뷰
    fun checkPersonalInfoLegacy(){
        textview_personal_information.setOnClickListener{
            try {
                ChromeTabHelper.launchChromeTab(this,
                    "https://stormbrainstorming.creatorlink.net/%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8")

            } catch (e: Exception) {
                startActivity(Intent(this,WebviewPersonalInfoLegacyActivity::class.java))

            }
        }

        textview_service_legacy.setOnClickListener{
            try {
                ChromeTabHelper.launchChromeTab(this,
                    "https://stormbrainstorming.creatorlink.net/%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80")

            } catch (e: Exception) {
                startActivity(Intent(this,WebviewPersonalInfoLegacyActivity::class.java))
            }
        }
    }
}
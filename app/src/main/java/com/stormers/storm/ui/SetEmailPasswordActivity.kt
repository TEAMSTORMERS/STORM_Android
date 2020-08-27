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
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.activity_set_email_password.button_back_signup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetEmailPasswordActivity : BaseActivity() {

    val buttonArray = ArrayList<StormDialogButton>()

    var isValidEmail = false
    var isValidPassword = false

    var lastEmailCheck = false
    var lastValidPassword = false
    var lastValidPasswordCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        checkPasswordTextWatcher()
        checkVaildEmailType()
        goSignUpActivity()
        goBackActivity()

        lastCheckNextButtonEnabled()
    }

    fun goBackActivity(){
        button_back_signup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun goSignUpActivity(){
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

    fun setRemoveAllTextWatcher() {
        //전체 지우기 버튼 활성화/비활성화
        edittext_input_email.setRemoveAllTextWatcher()
        edittext_input_password.setRemoveAllTextWatcher()
        edittext_password_check.setRemoveAllTextWatcher()
    }

    fun lastCheckNextButtonEnabled() {
        if(lastEmailCheck == true && lastValidPassword == true && lastValidPasswordCheck == true && isCheckedLegacy() == true){
            usableNextButton()
        } else {
            unusableNextButton()
        }
    }

    fun checkVaildEmailType() {

        edittext_input_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                isValidEmail = checkEmailCondition(edittext_input_email)

                if( isValidEmail == true) {
                    lastEmailCheck = true
                } else {
                    lastEmailCheck = false
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    fun checkPasswordTextWatcher() {

        edittext_input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                isValidPassword = checkPasswordCondition(edittext_input_password, edittext_password_check, textview_password_warning)

                if(isValidPassword == true) {
                    lastValidPassword = true
                } else {
                    lastValidPassword = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        edittext_password_check.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                isValidPassword = checkPasswordCondition(edittext_password_check, edittext_input_password, textview_password_warning)

                if( isValidPassword == true) {
                    lastValidPasswordCheck = true
                } else {
                    lastValidPasswordCheck = false
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        setRemoveAllTextWatcher()

    }

    fun checkPasswordCondition(editText1: StormEditText, editText2: StormEditText, textView: TextView) : Boolean {

        if (editText1.text!!.length < 8){
            textView.visibility = View.VISIBLE
            textView.setText(R.string.input_password_more_char)


            return false

        } else {

            if (!editText1.text.toString().equals(editText2.text.toString())){
                textView.setText(R.string.check_password)
                textView.visibility = View.VISIBLE

                return false

            } else {
                textView.visibility = View.GONE

                return true
            }
        }
    }

    fun checkEmailCondition(editText: StormEditText) : Boolean {

        if(isEmailValid(editText.text.toString()) == false){
            textview_email_warning.setText(R.string.check_email_type)
            textview_email_warning.visibility = View.VISIBLE

            return false

        } else {
            textview_email_warning.visibility = View.GONE

            return true
        }

    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isCheckedLegacy() : Boolean {

        if(checkbox_service_lagacy.isChecked == true && checkbox_personal_information.isChecked == true){

            return true

        } else {

            return false
        }

    }

    private fun unusableNextButton(){
        button_next_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
        button_next_signup.isEnabled = false

    }

    private fun usableNextButton(){
        button_next_signup.setBackgroundResource(R.drawable.button_activated_signup)
        button_next_signup.isEnabled = true
    }
}
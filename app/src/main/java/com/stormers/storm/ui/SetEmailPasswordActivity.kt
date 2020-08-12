package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View

import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_set_email_password.*

class SetEmailPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        signUpTextWatcher()
        goBackActivity()
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

        button_next_signup.setOnClickListener(){

            val intent = Intent(this, CompleteSignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
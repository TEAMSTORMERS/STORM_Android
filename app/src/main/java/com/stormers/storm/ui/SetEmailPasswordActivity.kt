package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlin.math.sign

class SetEmailPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        signUpTextWatcher()

        button_next_signup.setOnClickListener(){

            val intent = Intent(this, CompleteSignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun signUpTextWatcher() {

        edittext_input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (edittext_input_password.text!!.length < 8){
                    textview_password_warning.visibility = View.VISIBLE
                    button_next_signup.setBackgroundDrawable(R.drawable.button_color_popup_line_gray.toDrawable())
                    button_next_signup.isEnabled = false

                } else {
                    textview_password_warning.visibility = View.GONE

                    edittext_password_check.addTextChangedListener(object  : TextWatcher{
                        override fun afterTextChanged(s: Editable?) {


                            if (!edittext_password_check.text.toString().equals(edittext_input_password.text.toString())){
                                textview_password_warning.setText(R.string.check_password)
                                textview_password_warning.visibility = View.VISIBLE
                                button_next_signup.setBackgroundDrawable(R.drawable.button_color_popup_line_gray.toDrawable())
                                button_next_signup.isEnabled = false
                            } else {
                                if(edittext_input_email.text.isNullOrBlank()){
                                    button_next_signup.setBackgroundDrawable(R.drawable.button_color_popup_line_gray.toDrawable())
                                    button_next_signup.isEnabled = false
                                    textview_password_warning.visibility = View.GONE
                                } else {
                                    textview_password_warning.visibility = View.GONE
                                    button_next_signup.setBackgroundDrawable(R.drawable.button_activated_signup.toDrawable())
                                    button_next_signup.isEnabled = true

                                }
                            }
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    })
                    textview_password_warning.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }
}
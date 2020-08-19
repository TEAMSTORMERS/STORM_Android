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
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.SignUpActivity.Companion.IS_DEFAULT_IMAGE
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.activity_set_email_password.button_back_signup
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.fragment_mypage_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetEmailPasswordActivity : BaseActivity() {

    val buttonArray = ArrayList<StormDialogButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        signUpTextWatcher()
        checkVaildEmailType()
        goSignUpActivity()
        goBackActivity()
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
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }



    fun goSignUpActivity(){
        button_next_signup.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            intent.putExtra("userEmail",edittext_input_email.text.toString())
            intent.putExtra("userPassword",edittext_input_password.text.toString())
            startActivity(intent)
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
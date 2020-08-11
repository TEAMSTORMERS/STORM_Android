package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlin.math.sign

class SetEmailPasswordActivity : BaseActivity() {

    lateinit var pref : SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val REQUEST_CODE_LOGIN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_email_password)

        signUp()
        signUpTextWatcher()
    }

    fun signUp(){

        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        editor = pref.edit()

        button_complete_signup.setOnClickListener(){
            if (edittext_input_email.text.isNullOrBlank() || edittext_input_password.text.isNullOrBlank()
                || edittext_password_check.text.isNullOrBlank()){

                Toast.makeText(this,"회원가입 정보를 채워주세요!", Toast.LENGTH_SHORT).show()

            } else {

                if(edittext_input_password.text.toString().equals(edittext_password_check.text.toString())){

                    /*
                    RetrofitClient.create(RequestSignUp::class.java).requestSignUp(
                        SignUpModel(
                            userName = edittext_name.text.toString(),
                            userEamil = edittext_email.text.toString(),
                            userPassword = edittext_password.text.toString(),
                            userProfileImage = imageview_member_profile.toString())
                    ).enqueue(object : Callback<ResponseSignUp>{
                        override fun onFailure(call: Call<ResponseSignUp>, t: Throwable) {
                            Log.d("회원가입 통신실패", "${t}")
                        }

                        override fun onResponse(
                            call: Call<ResponseSignUp>,
                            response: Response<ResponseSignUp>
                        ) {
                            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                            startActivity(intent)
                        }
                    })

                     */

                    val intent = Intent(this, SignUpActivity::class.java)

                    editor.putString("email", edittext_input_email.text.toString())
                    editor.putString("password", edittext_input_password.text.toString())

                    intent.putExtra("name", edittext_input_email.text.toString())
                    intent.putExtra("password", edittext_input_password.text.toString())
                    intent.putExtra("user_profile", edittext_password_check.toString())

                    startActivity(intent)
                    // startActivityForResult(intent,REQUEST_CODE_LOGIN)

                } else {

                    Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않아요", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    fun signUpTextWatcher() {

        edittext_input_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (edittext_input_password.text!!.length >= 1 && edittext_input_password.text!!.length < 8){
                    textview_password_warning.setText("8자 이상 입력해주세요")
                    textview_password_warning.visibility = View.VISIBLE
                } else {
                    textview_password_warning.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

    }

}
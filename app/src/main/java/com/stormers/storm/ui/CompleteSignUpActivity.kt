package com.stormers.storm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stormers.storm.R
import kotlinx.android.synthetic.main.activity_complete_sign_up.*

class CompleteSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)

        goToLogInActivity()
    }

    fun goToLogInActivity() {
        button_goto_login.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}
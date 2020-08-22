package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.stormers.storm.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initSplashAnimation()

        //3초간 splash 애니메이션이 동작한 후 loginActivity로 넘어감
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1100)

    }

    private fun initSplashAnimation() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_splash)
        animationView.setAnimation("splash_real.json")
        animationView.playAnimation()
    }
}

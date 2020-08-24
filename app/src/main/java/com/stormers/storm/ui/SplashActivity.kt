package com.stormers.storm.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.stormers.storm.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Todo: 뒤에 공백이 없는 애니메이션으로 교체가 필요합니다!
        initSplashAnimation()

    }

    private fun initSplashAnimation() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_splash)
        animationView.setAnimation("splash_0824.json")
        animationView.playAnimation()

        animationView.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                //splash 애니메이션이 종료되면 loginActivity로 넘어감
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }
}

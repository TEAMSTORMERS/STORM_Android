package com.stormers.storm.splash

import android.animation.Animator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.stormers.storm.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LottieAnimationView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (view as LottieAnimationView).run {
            setAnimation("splash_real_0825.json")
            playAnimation()
            scaleType = ImageView.ScaleType.CENTER_CROP

            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {}
            })
        }
    }
}
package com.stormers.storm.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2

private const val MIN_SCALE = 0.75f

class DepthPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            when {
                position < -1 -> { // [-Infinity,-1)
                    alpha = 0f
                }
                position < 0 -> { // [-1,0]

                    alpha = 1 + position

                    translationX = pageWidth * -position
                    translationZ = -1f

                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }

                position == 1f -> {
                    alpha = 1f
                    translationX = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }

                position < 1 -> { // (0,1]
                    alpha = 1 - position

                    translationX = pageWidth * -position
                    translationZ = -1f

                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> { // (1,+Infinity]
                    alpha = 0f
                }
            }
        }
    }
}

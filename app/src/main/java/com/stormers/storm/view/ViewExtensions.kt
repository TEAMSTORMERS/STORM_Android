package com.stormers.storm.view

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorRes

fun View.setBackgroundTint(@ColorRes resId: Int) {
    backgroundTintList = ColorStateList.valueOf(context.getColor(resId))
}
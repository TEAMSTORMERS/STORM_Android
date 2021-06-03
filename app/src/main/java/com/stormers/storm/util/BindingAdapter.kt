package com.stormers.storm.util

import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

object BindingAdapter {

    @BindingAdapter("app:useCircleOutlineWithRadius")
    @JvmStatic
    fun ShapeableImageView.useCircleOutlineWithRadius(radius: Float) {
        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
    }
}
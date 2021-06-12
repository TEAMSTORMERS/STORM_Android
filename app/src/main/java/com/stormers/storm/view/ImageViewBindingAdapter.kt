package com.stormers.storm.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.stormers.storm.R

object ImageViewBindingAdapter {

    @BindingAdapter("setCircleOutline")
    @JvmStatic
    fun ShapeableImageView.setCircleOutline(radius: Float) {
        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
    }

    @BindingAdapter("loadImageUrl")
    @JvmStatic
    fun AppCompatImageView.loadImageUrl(url: String?) {
        if(url != null) {
            Glide.with(context).load(url).into(this)
        }
    }
}
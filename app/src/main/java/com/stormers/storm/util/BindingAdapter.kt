package com.stormers.storm.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.stormers.storm.R

object BindingAdapter {

    @BindingAdapter("setCircleOutline")
    @JvmStatic
    fun ShapeableImageView.setCircleOutline(radius: Float) {
        shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
    }

    @BindingAdapter("loadImageUrl")
    @JvmStatic
    fun ImageView.loadImageUrl(url: String?) {
        if(url == null) {
            setBackgroundResource(R.drawable.ic_launcher_background)
        } else {
            Glide.with(context).load(url).into(this)
        }
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun TextView.setText(content: MutableLiveData<String>) {
        if(content != null) {
            text = content.value
        } else {
            text = ""
        }
    }

    @InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
    @JvmStatic
    fun EditText.getText(): String {
        return text.toString()
    }

    @BindingAdapter("textAttrChanged")
    @JvmStatic
    fun EditText.setTextWatcher(textAttrChanged: InverseBindingListener?) {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                textAttrChanged?.onChange()
            }
        })
    }
}
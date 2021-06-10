package com.stormers.storm.view

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object RoundEditTextBindingAdapters {

    @BindingAdapter("setText")
    @JvmStatic
    fun setText(view: RoundEditText, text: String?) {
        if (view.text != text) {
            view.text = text
        }
    }

    @InverseBindingAdapter(attribute = "setText", event = "textAttrChanged")
    @JvmStatic
    fun getText(view: RoundEditText) : String? {
        return view.text
    }

    @BindingAdapter("textAttrChanged")
    @JvmStatic
    fun setListener(view: RoundEditText, listener: InverseBindingListener) {
        view.addOnTextChangedListener(listener::onChange)
    }
}
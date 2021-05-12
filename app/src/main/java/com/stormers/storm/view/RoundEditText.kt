package com.stormers.storm.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.stormers.storm.R
import com.stormers.storm.databinding.ViewRoundEditTextBinding

class RoundEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private val drawables = arrayOf(R.drawable.ic_email, R.drawable.ic_password)
    }

    private val binding: ViewRoundEditTextBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_round_edit_text,
        this, true
    )

    private val leftImageView = binding.imageviewRoundeditLeft
    private val editText = binding.edittextRoundeidt
    private val removeButton = binding.buttonRoundeditRemove

    var text: String?
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    init {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RoundEditText,
            0, 0
        )

        if (typeArray.hasValue(R.styleable.RoundEditText_android_hint)) {
            editText.hint = typeArray.getString(R.styleable.RoundEditText_android_hint)
        }

        if (typeArray.hasValue(R.styleable.RoundEditText_removeAll)) {
            removeButton.visibility = if (typeArray.getBoolean(R.styleable.RoundEditText_removeAll, false)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        if (typeArray.hasValue(R.styleable.RoundEditText_drawable)) {
            leftImageView.run {
                visibility = View.VISIBLE
                setImageResource(drawables[typeArray.getInteger(R.styleable.RoundEditText_drawable, 0)])
            }
        }

        typeArray.recycle()
    }

    fun addOnTextChangedListener(listener: () -> Unit) {
        binding.edittextRoundeidt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.invoke()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }
}
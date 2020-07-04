package com.stormers.storm.customwidget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.stormers.storm.R
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.dialog_custom.view.imageview_dialog_symbol

class StormDialog(private val dialogType: Int, val message: String) : DialogFragment() {

    companion object {
        const val TAG = "storm_dialog"

        const val NORMAL_WITH_ONE_BUTTON = 0
        const val CODE_FOR_PARTICIPATION = 1
        const val WAITING_NO_BUTTON = 2
    }

    var listener: OnClickListener? = null

    constructor(dialogType: Int, message: String, listener: OnClickListener) : this(dialogType, message) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container)

        when (dialogType) {
            NORMAL_WITH_ONE_BUTTON -> {
                view.group_dialog_code.visibility = View.GONE
                view.textview_dialog_title.text = message
            }

            CODE_FOR_PARTICIPATION -> {
                view.group_dialog_code.visibility = View.VISIBLE
            }

            else -> {
                view.imageview_dialog_symbol.setImageResource(R.drawable.h_roundstart_popup_symbol)
                view.group_dialog_code.visibility = View.GONE
                view.constraintLayout_dialog_button.visibility = View.GONE
                view.view_dialog_divider2.visibility = View.INVISIBLE
                view.textview_dialog_title.text = message
            }
        }

        //직각 모서리를 없애기 위함
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        view.constraintLayout_dialog_button.setOnClickListener {
            listener?.onClick()
            dismiss()
        }

        return view
    }

    interface OnClickListener {
        fun onClick()
    }
}
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

class StormDialog : DialogFragment() {
    companion object {
        const val TAG = "storm_dialog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container)

        //직각 모서리를 없애기 위함
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        view.constraintLayout_dialog_button.setOnClickListener {
            dismiss()
        }

        return view
    }
}
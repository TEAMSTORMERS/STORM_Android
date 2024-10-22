package com.stormers.storm.customview.dialog

data class StormDialogButton(
    val text: String,
    val accentColor: Boolean,
    var listener: OnClickListener?
) {
    var pickerListener: OnPickerClickListener? = null

    interface OnClickListener {
        fun onClick()
    }

    interface OnPickerClickListener {
        fun onClick(minute: Int)
    }
}
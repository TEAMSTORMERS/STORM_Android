package com.stormers.storm.customview.dialog

data class StormDialogButton(
    val text: String,
    val accentColor: Boolean,
    val listener: OnClickListener
) {
    interface OnClickListener {
        fun onClick()
    }
}
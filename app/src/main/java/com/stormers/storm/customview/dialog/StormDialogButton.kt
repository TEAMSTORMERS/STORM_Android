package com.stormers.storm.customview.dialog

data class StormDialogButton(
    val text: String,
    val listener: OnClickListener
) {
    interface OnClickListener {
        fun onClick()
    }
}
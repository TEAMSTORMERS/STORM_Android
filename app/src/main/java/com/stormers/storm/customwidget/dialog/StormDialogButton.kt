package com.stormers.storm.customwidget.dialog

data class StormDialogButton(
    val text: String,
    val listener: OnClickListener
) {
    interface OnClickListener {
        fun onClick()
    }
}
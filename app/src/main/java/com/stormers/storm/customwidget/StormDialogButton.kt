package com.stormers.storm.customwidget

data class StormDialogButton(
    val text: String,
    val listener: OnClickListener
) {
    interface OnClickListener {
        fun onClick()
    }
}
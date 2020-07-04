package com.stormers.storm.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.customwidget.StormDialog

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun showDialog(dialog: Int, message: String) {
        StormDialog(dialog, message).show(supportFragmentManager, StormDialog.TAG)
    }

    protected fun showDialog(dialog: Int, message: String, listener: StormDialog.OnClickListener) {
        StormDialog(dialog, message, listener).show(supportFragmentManager, StormDialog.TAG)
    }
}
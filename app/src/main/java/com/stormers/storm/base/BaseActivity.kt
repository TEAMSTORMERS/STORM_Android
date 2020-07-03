package com.stormers.storm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.customwidget.StormDialog

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun showDialog(message: String) {
        StormDialog().show(supportFragmentManager, StormDialog.TAG)
    }
}
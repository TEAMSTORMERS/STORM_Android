package com.stormers.storm.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.stormers.storm.customview.dialog.StormLoadingDialog
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.SharedPreference

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentId: Int? = null

    protected val preference: SharedPreference by lazy { GlobalApplication.prefs }

    private val loadingDialog: DialogFragment by lazy { StormLoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentId = initFragmentId()
    }

    protected open fun initFragmentId(): Int? {
        return null
    }

    fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, "loading_dialog")
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    fun goToFragment(cls: Class<*>, args: Bundle?) {
        try {
            val fragment = cls.newInstance() as Fragment
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(fragmentId!!, fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
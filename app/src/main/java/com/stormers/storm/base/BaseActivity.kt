package com.stormers.storm.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.customview.dialog.StormLoadingDialog
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.KeyBoardVisibilityUtils
import com.stormers.storm.util.SharedPreference
import java.net.URL

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentId: Int? = null

    protected val preference: SharedPreference by lazy { GlobalApplication.prefs }

    private val loadingDialog: DialogFragment by lazy { StormLoadingDialog() }

    protected val TAG = javaClass.simpleName

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

    fun goToFragment(cls: Class<*>, args: Bundle?): Fragment? {
        try {
            val fragment = cls.newInstance() as Fragment
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(fragmentId!!, fragment).commit()
            return fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun addFragment(cls: Class<*>, args: Bundle?) : Fragment? {
        try {
            val fragment = cls.newInstance() as Fragment
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().add(fragmentId!!, fragment).commit()
            return fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun hideFragment(fragment: Fragment) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().hide(fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showFragment(fragment: Fragment) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().show(fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun scrollViewKeyBoard(scrollView: ScrollView){

      KeyBoardVisibilityUtils(window,
            onShowKeyboard = {
                    keyboardHeight ->
                scrollView.run {
                    smoothScrollTo(scrollX,scrollY + keyboardHeight)
                }

            })
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun moveToWebView(webView: WebView, url: String) {

        webView.settings.javaScriptEnabled = true

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(url)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
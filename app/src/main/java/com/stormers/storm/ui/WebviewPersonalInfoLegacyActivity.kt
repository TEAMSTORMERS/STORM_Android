package com.stormers.storm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview_personal_info_legacy.*

class WebviewPersonalInfoLegacyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_personal_info_legacy)

        moveToWebView(webview_for_personal_info_legacy, "https://stormbrainstorming.creatorlink.net/%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8")

    }
}
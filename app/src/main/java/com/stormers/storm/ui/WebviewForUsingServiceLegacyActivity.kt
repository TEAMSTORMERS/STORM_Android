package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview_for_using_service_legacy.*

class WebviewForUsingServiceLegacyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_for_using_service_legacy)

        moveToWebView(webview_for_use_legacy,"https://stormbrainstorming.creatorlink.net/%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80")
    }
}
package com.stormers.storm.util

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity

object ChromeTabHelper {

    fun launchChromeTab(activity: BaseActivity, url: String) {

        val builder = CustomTabsIntent.Builder()
        builder.run {
            setToolbarColor(activity.getColor(R.color.storm_white))
            setShowTitle(true)
        }

        val intent = builder.build();
        intent.launchUrl(activity, Uri.parse(url));
    }
}

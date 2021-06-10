package com.stormers.storm.ui

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.round.model.RoundModel
//import com.stormers.storm.util.DatabaseManager
import com.stormers.storm.util.SharedPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        instance = this

        prefs = SharedPreference(applicationContext)
        //databaseManager = DatabaseManager.getInstance(this)

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) { }

            override fun onActivityStarted(p0: Activity) { }

            override fun onActivityDestroyed(p0: Activity) { }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) { }

            override fun onActivityStopped(p0: Activity) { }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                p0.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            override fun onActivityResumed(p0: Activity) { }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    companion object {
        var instance: GlobalApplication? = null

        //lateinit var databaseManager: DatabaseManager

        lateinit var prefs: SharedPreference

        var profileBitmap : Bitmap? = null

        var userIdx: Int = -1

        var currentProject: ProjectModel? = null

        var currentRound: RoundModel? = null

        var isHost: Boolean = false

        var projectPreviewIsDirty = false
    }
}
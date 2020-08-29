package com.stormers.storm.ui

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import com.kakao.auth.KakaoSDK
import com.stormers.storm.kakao.KakaoSDKAdapter
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.round.model.RoundModel
//import com.stormers.storm.util.DatabaseManager
import com.stormers.storm.util.SharedPreference

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())

        prefs = SharedPreference(applicationContext)
        //databaseManager = DatabaseManager.getInstance(this)

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) {
            "this application does not inherit com.kakao.GlobalApplication"
        }
        return instance!!
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
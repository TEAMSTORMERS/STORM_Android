package com.stormers.storm.ui

import android.app.Application
import com.kakao.auth.KakaoSDK
import com.stormers.storm.kakao.KakaoSDKAdapter
import com.stormers.storm.project.model.ProjectEntity
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.util.DatabaseManager
import com.stormers.storm.util.SharedPreference

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())

        prefs = SharedPreference(applicationContext)
        databaseManager = DatabaseManager.getInstance(this)
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

        lateinit var databaseManager: DatabaseManager

        lateinit var prefs: SharedPreference


        var userIdx: Int = -1

        var currentProject: ProjectModel? = null

        var currentRound: RoundModel? = null

        var isHost: Boolean = false
    }
}
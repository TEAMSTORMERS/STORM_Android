package com.stormers.storm.ui

import android.app.Application
import android.graphics.Bitmap
import com.kakao.auth.KakaoSDK
import com.stormers.storm.kakao.KakaoSDKAdapter
import com.stormers.storm.util.SharedPreference


//fixme: 여기에 문제가 있을까????
class GlobalApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
        prefs = SharedPreference(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance= null
    }

    fun getGlobalApplicationContext():GlobalApplication{
        checkNotNull(instance){
            "this application does not inherit com.kakao.GlobalApplication"}
        return instance!!
    }
    companion object{
        var instance: GlobalApplication? = null

        lateinit var prefs: SharedPreference

        var profileBitmap : Bitmap? = null
    }
}
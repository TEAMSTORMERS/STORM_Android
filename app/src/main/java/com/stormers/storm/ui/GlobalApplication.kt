package com.stormers.storm.ui

import android.app.Application
import com.kakao.auth.*
import com.kakao.auth.KakaoSDK
import com.stormers.storm.kakao.KakaoSDKAdapter


//fixme: 여기에 문제가 있을까????
class GlobalApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
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
    }
}
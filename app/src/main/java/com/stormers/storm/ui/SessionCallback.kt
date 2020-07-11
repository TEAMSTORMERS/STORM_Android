package com.stormers.storm.ui

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.kakao.auth.ISession
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException

//fixme: 여기에 문제가 있을까????
class SessionCallback(private val startInetnt : () -> Unit) : ISessionCallback{

    override fun onSessionOpenFailed(exception: KakaoException?) {
        Log.e("KaKaoLogin","Session Call back :: onSessionOpenFailed ${exception?.message}")
    }
    override fun onSessionOpened() {

        UserManagement.getInstance().me(object : MeV2ResponseCallback() {

            override fun onFailure(erroResult: ErrorResult?) {
                Log.i("KaKaoLogin", "Session Call back:: on failed ${erroResult?.errorMessage}")
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.i("KaKaoLogin", "Session Call back:: on Closed ${errorResult?.errorMessage}")
            }

            override fun onSuccess(result: MeV2Response?) {
                Log.d("KaKaoLogin","성공했습니다.")
                startInetnt()
                checkNotNull(result) { "session response null" }
                // register or log
            }
        })
    }
}
package com.stormers.storm.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    companion object {
        const val USER_IDX = "user_idx"
        const val AUTO_LOGIN = "auto_login"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    private fun getString(key: String): String? {
        val str = prefs.getString(key, "null")

        return if (str.equals("null")) {
            null
        } else {
            return str
        }
    }

    private fun setString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    private fun setBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    private fun getInt(key: String): Int? {
        val int = prefs.getInt(key, -1)

        return if (int == -1) {
            null
        } else {
            int
        }
    }

    private fun setInt(key: String, value: Int?) {
        if (value == null) {
            prefs.edit().putInt(key, -1).apply()
        } else {
            prefs.edit().putInt(key, value).apply()
        }
    }

    fun setUserIdx(userIdx:Int)  {
      setInt(USER_IDX, userIdx)
    }

    fun getUserIdx(): Int? {
        return getInt(USER_IDX)
    }

    fun setAutoLogIn(auto_login : Boolean) {
        setBoolean(AUTO_LOGIN, auto_login)
    }

    fun getAutoLogIn() : Boolean{
        return getBoolean(AUTO_LOGIN)
    }

}


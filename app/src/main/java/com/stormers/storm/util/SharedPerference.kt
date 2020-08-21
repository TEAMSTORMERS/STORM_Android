package com.stormers.storm.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    companion object {
        const val PROJECT_IDX = "projectIdx"
        const val ROUND_IDX = "roundIdx"
        const val PROJECT_NAME = "projectName"
        const val ROUND_COUNT = "roundCount"
        const val USER_IDX = "user_idx"
        const val PROJECT_CODE = "project_code"
        const val HOST = "is_host"
        const val AUTO_LOGIN = "auto_login"
        const val PROFILE_COLOR = "profile_color"

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

    fun getProjectIdx(): Int? {
        return getInt(PROJECT_IDX)
    }

    fun setProjectIdx(projectIdx: Int?) {
        setInt(PROJECT_IDX, projectIdx)
    }

    fun getRoundIdx(): Int? {
        return getInt(ROUND_IDX)
    }

    fun setRoundIdx(roundIdx: Int?) {
        setInt(ROUND_IDX, roundIdx)
    }

    fun setProjectName(projectName: String?) {
        setString(PROJECT_NAME, projectName)
    }

    fun getProjectName(): String? {
        return getString(PROJECT_NAME)
    }

    fun getRoundCount(): Int? {
        return getInt(ROUND_COUNT)
    }

    fun setRoundCount(roundCount: Int?) {
        setInt(ROUND_COUNT, roundCount)
    }

    fun setUserIdx(userIdx:Int)  {
      setInt(USER_IDX, userIdx)
    }

    fun getUserIdx(): Int? {
        return getInt(USER_IDX)
    }

    fun setProjectCode(code: String?)  {
        setString(PROJECT_CODE, code)
    }

    fun getProjectCode(): String? {
        return getString(PROJECT_CODE)
    }

    fun setHost(isHost: Boolean)  {
        setBoolean(HOST, isHost)
    }

    fun isHost(): Boolean {
        return getBoolean(HOST)
    }

    fun setAutoLogIn(auto_login : Boolean) {
        setBoolean(AUTO_LOGIN, auto_login)
    }

    fun getAutoLogIn() : Boolean{
        return getBoolean(AUTO_LOGIN)
    }

    fun setProfileColor(profileColor: Int) {
        setInt(PROFILE_COLOR, profileColor)
    }

    fun getProfileColor(): Int? {
        return getInt(PROFILE_COLOR)
    }
}


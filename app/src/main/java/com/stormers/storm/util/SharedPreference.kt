package com.stormers.storm.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    companion object {
        const val PROJECT_IDX = "projectIdx"
        const val ROUND_IDX = "roundIdx"
        const val USER_ID = "userId"
        const val ROUND_COUNT = "roundCount"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    private fun getString(key: String) : String? {
        val str = prefs.getString(key, "null")

        return if (str.equals("null")) {
            null
        } else {
            return str
        }
    }

    private fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    private fun getInt(key: String) : Int? {
        val int = prefs.getInt(key, -1)

        return if (int == -1) {
            null
        } else {
            int
        }
    }

    private fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getProjectIdx(): Int? {
        return getInt(PROJECT_IDX)
    }

    fun setProjectIdx(projectIdx: Int) {
        setInt(PROJECT_IDX, projectIdx)
    }

    fun getRoundIdx(): Int? {
        return getInt(ROUND_IDX)
    }

    fun setRoundIdx(roundIdx: Int) {
        setInt(ROUND_IDX, roundIdx)
    }

    fun getUserId(): Int? {
        return getInt(USER_ID)
    }

    fun setUserId(userId: Int) {
        setInt(USER_ID, userId)
    }

    fun getRoundCount(): Int? {
        return getInt(ROUND_COUNT)
    }

    fun setRoundCount(roundCount: Int) {
        setInt(ROUND_COUNT, roundCount)
    }
}

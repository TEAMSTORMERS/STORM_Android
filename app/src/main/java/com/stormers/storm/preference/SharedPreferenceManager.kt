package com.stormers.storm.preference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    companion object {
        const val SHARED_PREFERENCES_FILE_NAME = "manitto_preference"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return getString(key, null)
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clear(): Boolean {
        return editor.clear().commit()
    }
}
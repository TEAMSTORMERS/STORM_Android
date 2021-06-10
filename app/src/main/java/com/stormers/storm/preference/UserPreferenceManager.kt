package com.stormers.storm.preference

class UserPreferenceManager(private val preferenceManager: SharedPreferenceManager) {

    companion object {
        private const val PREF_KEY_USER_ID = "user_id"
        private const val PREF_KEY_AUTO_LOGIN = "auto_login"
    }

    fun setUserId(userId: Int) {
        preferenceManager.setInt(PREF_KEY_USER_ID, userId)
    }

    fun getUserId(): Int {
        return preferenceManager.getInt(PREF_KEY_USER_ID, -1)
    }

    fun setAutoLogin(isAutoLogin: Boolean) {
        preferenceManager.setBoolean(PREF_KEY_AUTO_LOGIN, isAutoLogin)
    }

    fun getAutoLogin(): Boolean {
        return preferenceManager.getBoolean(PREF_KEY_AUTO_LOGIN, false)
    }
}
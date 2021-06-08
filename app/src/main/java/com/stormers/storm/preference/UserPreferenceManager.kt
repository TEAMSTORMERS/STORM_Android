package com.stormers.storm.preference

class UserPreferenceManager(private val preferenceManager: SharedPreferenceManager) {

    companion object {
        private const val PREF_KEY_USER_ID = "user_id"
    }

    fun setUserId(userId: Int) {
        preferenceManager.setInt(PREF_KEY_USER_ID, userId)
    }

    fun getUserId(): Int {
        return preferenceManager.getInt(PREF_KEY_USER_ID, -1)
    }
}
package com.stormers.storm.user

import android.util.Log
import com.stormers.storm.ui.GlobalApplication

class UserRepository {

    companion object {
        private const val TAG = "UserRepository"

        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            if (instance == null) {
                synchronized(UserRepository::class.java) {
                    if (instance == null) {
                        instance = UserRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val dao: UserDao by lazy { GlobalApplication.databaseManager.userDao() }

    fun get(userIdx: Int, callback: GetUserCallback) {
        val result = dao.get(userIdx)
        Log.d(TAG, "get: $result")
        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onUserLoaded(result)
        }
    }

    fun getAll(usersIdx: List<Int>): List<UserModel> {
        val users = mutableListOf<UserModel>()
        for (userIdx in usersIdx) {
            val user = dao.get(userIdx)

            if (user != null) {
                users.add(user)
            } else {
                Log.e(TAG, "No user : $userIdx")
            }
        }
        return users
    }

    fun insert(user: UserModel) {
        dao.insert(user)
        Log.d(TAG, "insert: $user")
    }

    interface LoadUsersCallback {

        fun onUsersLoaded(users: List<UserModel>)

        fun onDataNotAvailable()
    }

    interface GetUserCallback {

        fun onUserLoaded(user: UserModel)

        fun onDataNotAvailable()
    }
}
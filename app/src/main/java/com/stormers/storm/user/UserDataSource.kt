package com.stormers.storm.user

interface UserDataSource {

    interface LoadUsersCallback<T> {

        fun onUsersLoaded(users: List<T>)

        fun onDataNotAvailable()
    }

    interface GetUserCallback<T> {

        fun onUserLoaded(user: T)

        fun onDataNotAvailable()
    }
}
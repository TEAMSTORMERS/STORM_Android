package com.stormers.storm.user

import com.stormers.storm.round.model.RoundModel

class UserRepository {

    interface LoadUsersCallback {

        fun onUsersLoaded(users: List<UserModel>)

        fun onDataNotAvailable()
    }

    interface GetUserCallback {

        fun onRoundLoaded(user: UserModel)

        fun onDataNotAvailable()
    }
}
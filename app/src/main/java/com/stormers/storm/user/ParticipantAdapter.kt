package com.stormers.storm.user

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class ParticipantAdapter : BaseAdapter<UserModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<UserModel> {
        return ParticipantViewHolder(parent)
    }
}
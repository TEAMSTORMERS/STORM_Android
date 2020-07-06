package com.stormers.storm

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class ParticipantAdapter : BaseAdapter<ParticipantModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ParticipantModel> {
        return ParticipantViewHolder(parent)
    }
}
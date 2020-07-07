package com.stormers.storm.card

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder


class CardAdapter : BaseAdapter<CardModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardModel> {
        return CardViewHolder(parent)
    }

}
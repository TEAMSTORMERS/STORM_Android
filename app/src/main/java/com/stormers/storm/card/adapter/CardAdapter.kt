package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.viewholder.CardViewHolder


class CardAdapter : BaseAdapter<CardModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardModel> {
        return CardViewHolder(parent)
    }

}
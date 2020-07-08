package com.stormers.storm.card

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.CardDetailViewHolder
import com.stormers.storm.card.CardModel

class ExpandCardAdapter : BaseAdapter<CardModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardModel> {
        return CardDetailViewHolder(parent)
    }
}
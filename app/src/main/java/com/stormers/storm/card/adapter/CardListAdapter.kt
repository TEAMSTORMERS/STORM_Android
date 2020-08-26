package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardWithOwnerModel
import com.stormers.storm.card.viewholder.CardListViewHolder

class CardListAdapter(private val listener: OnCardClickListener?) : BaseAdapter<CardWithOwnerModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardWithOwnerModel> {
        return CardListViewHolder(parent, listener)
    }

    interface OnCardClickListener {
        fun onCardClick(cardIdx: Int)
    }
}
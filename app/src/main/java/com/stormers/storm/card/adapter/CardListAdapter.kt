package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.viewholder.CardListViewHolder

class CardListAdapter(private val showHeart: Boolean, private val listener: OnCardClickListener?) : BaseAdapter<CardEnumModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardEnumModel> {
        return CardListViewHolder(parent, showHeart, listener)
    }

    interface OnCardClickListener {
        fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int)
    }
}
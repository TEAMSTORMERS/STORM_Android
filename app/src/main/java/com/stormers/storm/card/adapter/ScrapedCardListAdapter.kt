package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo
import com.stormers.storm.card.viewholder.ScrapedCardViewHolder

class ScrapedCardListAdapter(private val showHeart: Boolean, val listener: OnCardClickListener): BaseAdapter<ScrapedCardWithRoundInfo>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ScrapedCardWithRoundInfo> {
        return ScrapedCardViewHolder(parent, showHeart, listener)
    }

    interface OnCardClickListener {
        fun onCardClick(cardIdx: Int)
    }
}
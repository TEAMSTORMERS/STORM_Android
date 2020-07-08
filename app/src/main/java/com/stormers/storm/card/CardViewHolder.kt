package com.stormers.storm.card

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_heart_card.view.*
import kotlinx.android.synthetic.main.view_card_custom.view.*

class CardViewHolder(parent: ViewGroup) : BaseViewHolder<CardModel>(R.layout.item_heart_card, parent) {

    override fun bind(data: CardModel) {
        itemView.stormcard_itemheart.heartState = data.isLiked
    }
}
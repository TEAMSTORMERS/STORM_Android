package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import kotlinx.android.synthetic.main.item_heart_card.view.*

class NoHeartCardViewHolder(parent: ViewGroup) : BaseViewHolder<CardModel>(R.layout.item_heart_card, parent) {

    override fun bind(data: CardModel) {
        itemView.stormcard_itemheart.showHeartButton(false)
        itemView.stormcard_itemheart.setImageUrl(data.url)

        itemView.stormcard_itemheart.heartState = data.isLiked
    }
}
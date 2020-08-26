package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.adapter.ScrapedCardListAdapter
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo
import kotlinx.android.synthetic.main.item_heart_card.view.*

class ScrapedCardViewHolder(parent: ViewGroup, val listener: ScrapedCardListAdapter.OnCardClickListener):
    BaseViewHolder<ScrapedCardWithRoundInfo>(R.layout.item_heart_card, parent) {

    override fun bind(data: ScrapedCardWithRoundInfo) {
        itemView.stormcard_itemheart.run {
            showHeartButton(false)

            if (data.cardImage != null) {
                setImageUrl(data.cardImage)
            } else {
                setText(data.cardText!!)
            }
            setOnClickListener {
                listener.onCardClick(data.cardIdx)
            }
        }
    }
}
package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.card.adapter.ExpandRoundCardAdapter
import com.stormers.storm.card.model.CardWithOwnerModel

class ExpandRoundCardViewHolder(parent: ViewGroup, val listener: ExpandRoundCardAdapter.OnScrapChangedCallback?) : BaseExpandCardViewHolder<CardWithOwnerModel>(parent) {

    override fun onCreateCardView(data: CardWithOwnerModel) {
        if (data.cardImage != null) {
            setImageUrl(data.cardImage)
        } else {
            setCardText(data.cardText!!)
        }

        applyHeart(data.isScraped == 1)
    }

    override fun onClickHeart(data: CardWithOwnerModel): Boolean {
        data.run {
            isScraped = if (isScraped == 1) {
                unScrapCard(data.cardIdx)
                0
            } else {
                scrapCard(data.cardIdx)
                1
            }
            listener?.onScrapChanged()

            return isScraped == 1
        }
    }

    override fun onOwnerImageAttached(data: CardWithOwnerModel): String {
        return data.userImg
    }
}

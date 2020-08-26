package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo

class ExpandScrapedCardViewHolder(parent: ViewGroup) : BaseExpandCardViewHolder<ScrapedCardWithRoundInfo>(parent) {

    override fun onCreateCardView(data: ScrapedCardWithRoundInfo) {
        if (data.cardImage != null) {
            setImageUrl(data.cardImage)
        } else {
            setCardText(data.cardText!!)
        }
    }

    override fun onClickHeart(data: ScrapedCardWithRoundInfo) : Boolean {
        data.run {
            if (isScraped) {
                unScrapCard(cardIdx)
            } else {
                scrapCard(cardIdx)
            }
            isScraped = !isScraped

            return isScraped
        }
    }

    override fun onOwnerImageAttached(data: ScrapedCardWithRoundInfo): String {
        return data.userImage
    }
}
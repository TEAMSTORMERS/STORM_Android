package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo

class ExpandScrapedCardViewHolder(parent: ViewGroup) : BaseExpandCardViewHolder<ScrapedCardWithRoundInfo>(parent) {

    override fun onCreateCardView(data: ScrapedCardWithRoundInfo) {

        //Fixme: 분명 true로 초기화해뒀는데 여기만 오면 false가 됨. 통신 후에는 false로 변하는건가? 우선은 강제로 true 먹임
        data.isScraped = true

        applyHeart(data.isScraped)

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
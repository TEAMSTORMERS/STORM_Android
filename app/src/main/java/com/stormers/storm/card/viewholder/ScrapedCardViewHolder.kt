package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.adapter.ScrapedCardListAdapter
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo
import com.stormers.storm.customview.StormCard
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.item_heart_card.view.*

class ScrapedCardViewHolder(parent: ViewGroup, private val showHeart: Boolean, val listener: ScrapedCardListAdapter.OnCardClickListener):
    BaseViewHolder<ScrapedCardWithRoundInfo>(R.layout.item_heart_card, parent) {

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(
        CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    override fun bind(data: ScrapedCardWithRoundInfo) {
        itemView.stormcard_itemheart.run {
            heartState = true

            if (data.cardImage != null) {
                setImageUrl(data.cardImage)
            } else {
                setText(data.cardText!!)
            }
            setOnClickListener {
                listener.onCardClick(data.cardIdx)
            }

            if (!showHeart) {
                showHeartButton(false)
            } else {
                setOnHeartStateChangedListener(object : StormCard.OnHeartStateChangedListener {
                    val body = ScrapedCardRelationModel(GlobalApplication.userIdx, data.cardIdx)
                    override fun onHeartStateChanged(state: Boolean) {
                        if (state) {
                            cardRepository.scrapCard(body)
                        } else {
                            cardRepository.unScrapCard(body)
                        }
                    }
                })
            }
        }
    }
}
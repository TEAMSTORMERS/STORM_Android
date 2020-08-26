package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.CardWithOwnerModel
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.customview.StormCard
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.item_heart_card.view.*

class CardListViewHolder(parent: ViewGroup, private val listener: CardListAdapter.OnCardClickListener?)
    : BaseViewHolder<CardWithOwnerModel>(R.layout.item_heart_card, parent) {

    companion object {
        private const val TAG = "SavedCardViewHolder"
    }

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    override fun bind(data: CardWithOwnerModel) {

        itemView.stormcard_itemheart.run {
            heartState = data.isScraped == 1

            if (data.cardImage != null) {
                setImageUrl(data.cardImage)
            } else {
                setText(data.cardText!!)
            }

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

        listener?.let {
            itemView.setOnClickListener {
                listener.onCardClick(data.cardIdx)
            }
        }

    }
}
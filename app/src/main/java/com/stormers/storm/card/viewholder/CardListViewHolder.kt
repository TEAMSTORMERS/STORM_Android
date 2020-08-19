package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.CardType
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.customview.StormCard
import kotlinx.android.synthetic.main.item_heart_card.view.*

class CardListViewHolder(parent: ViewGroup, private val showHeart: Boolean,
                         private val listener: CardListAdapter.OnCardClickListener?)
    : BaseViewHolder<CardEnumModel>(R.layout.item_heart_card, parent) {

    companion object {
        private const val TAG = "SavedCardViewHolder"
    }

    private val cardRepository: CardRepository by lazy { CardRepository() }

    override fun bind(data: CardEnumModel) {

        if (!showHeart) {
            itemView.stormcard_itemheart.showHeartButton(false)
        } else {
            itemView.stormcard_itemheart.heartState = data.isScraped == true
        }

        listener?.let {
            itemView.setOnClickListener {
                listener.onCardClick(data.projectIdx, data.roundIdx, data.cardIdx)
            }
        }

        //카드 타입이 그림인 경우
        if (data.cardType == CardType.DRAWING) {

            if (data.cardContent.substring(0, 5) == "https") {
                itemView.stormcard_itemheart.setImageUrl(data.cardContent)
            } else {

                val bitmap = BitmapConverter.stringToBitmap(data.cardContent)
                bitmap?.let {
                    itemView.stormcard_itemheart.setBitmap(it)
                }
            }

        //카드 타입이 텍스트인 경우
        } else {
            data.cardContent.let {
                itemView.stormcard_itemheart.setText(it)
            }
        }

        itemView.stormcard_itemheart.setOnHeartStateChangedListener(object : StormCard.OnHeartStateChangedListener {
            override fun onHeartStateChanged(state: Boolean) {
                data.isScraped = state

                cardRepository.update(data)
            }

        })
    }
}
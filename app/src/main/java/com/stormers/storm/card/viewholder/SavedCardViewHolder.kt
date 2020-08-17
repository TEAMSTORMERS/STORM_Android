package com.stormers.storm.card.viewholder

import android.util.Log
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.customview.StormCard
import kotlinx.android.synthetic.main.item_heart_card.view.*

class SavedCardViewHolder(parent: ViewGroup, private val showHeart: Boolean,
                          private val listener: SavedCardAdapter.OnCardClickListener?)
    : BaseViewHolder<SavedCardEntity>(R.layout.item_heart_card, parent) {

    companion object {
        private const val TAG = "SavedCardViewHolder"
    }

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(itemView.context) }

    override fun bind(data: SavedCardEntity) {

        if (!showHeart) {
            itemView.stormcard_itemheart.showHeartButton(false)
        } else {
            itemView.stormcard_itemheart.heartState = data.isScraped == SavedCardEntity.TRUE
        }

        listener?.let {
            itemView.setOnClickListener {
                if (data.projectIdx != null && data.roundIdx != null && data.cardIdx != null) {
                    listener.onCardClick(data.projectIdx, data.roundIdx, data.cardIdx)
                } else {
                    Log.e(TAG, "Wrong card")
                }
            }
        }

        //카드 타입이 그림인 경우
        if (data.cardType == SavedCardEntity.DRAWING) {

            if (data.content?.substring(0, 5).equals("https")) {
                itemView.stormcard_itemheart.setImageUrl(data.content!!)
            } else {

                val bitmap = BitmapConverter.stringToBitmap(data.content)
                bitmap?.let {
                    itemView.stormcard_itemheart.setBitmap(it)
                }
            }

        //카드 타입이 텍스트인 경우
        } else {
            data.content?.let {
                itemView.stormcard_itemheart.setText(it)
            }
        }

        itemView.stormcard_itemheart.setOnHeartStateChangedListener(object : StormCard.OnHeartStateChangedListener {
            override fun onHeartStateChanged(state: Boolean) {
                data.isScraped = if (state) {
                    SavedCardEntity.TRUE
                } else {
                    SavedCardEntity.FALSE
                }

                savedCardRepository.update(data)
            }

        })
    }
}
package com.stormers.storm.card.viewholder

import android.graphics.Bitmap
import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.item_heart_card.view.*

class SavedCardViewHolder(parent: ViewGroup) : BaseViewHolder<SavedCardEntity>(R.layout.item_heart_card, parent) {
    override fun bind(data: SavedCardEntity) {
        if (data.cardType == SavedCardEntity.DRAWING) {
            val bitmap = BitmapConverter.stringToBitmap(data.content)

            bitmap?.let {
                itemView.stormcard_itemheart.setBitmap(bitmap)
            }
        } else {
            //텍스트 형식 추가
            //itemView.stormcard_itemheart.setText(data.content)
        }
    }
}
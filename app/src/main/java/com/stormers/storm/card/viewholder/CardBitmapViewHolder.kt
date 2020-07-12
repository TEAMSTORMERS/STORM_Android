package com.stormers.storm.card.viewholder

import android.graphics.Bitmap
import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_heart_card.view.*

class CardBitmapViewHolder(parent: ViewGroup) : BaseViewHolder<Bitmap>(R.layout.item_heart_card, parent) {
    override fun bind(data: Bitmap) {
        itemView.stormcard_itemheart.setBitmap(data)
    }
}
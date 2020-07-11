package com.stormers.storm.card.adapter

import android.graphics.Bitmap
import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.viewholder.CardBitmapViewHolder
import com.stormers.storm.card.viewholder.CardViewHolder

class CardBitmapAdapter : BaseAdapter<Bitmap>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Bitmap> {
        return CardBitmapViewHolder(parent)
    }
}
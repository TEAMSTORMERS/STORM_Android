package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.SimpleCardModel
import com.stormers.storm.card.viewholder.CardPreviewViewHolder

class CardPreviewAdapter : BaseAdapter<SimpleCardModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SimpleCardModel> {
        return CardPreviewViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return 4
    }
}
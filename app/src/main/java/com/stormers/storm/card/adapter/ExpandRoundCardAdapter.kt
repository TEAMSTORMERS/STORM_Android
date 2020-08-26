package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.fragment.ExpandRoundCardFragment
import com.stormers.storm.card.model.CardWithOwnerModel
import com.stormers.storm.card.viewholder.ExpandRoundCardViewHolder

class ExpandRoundCardAdapter : BaseAdapter<CardWithOwnerModel>() {

    private var scrapChangedListener: OnScrapChangedCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardWithOwnerModel> {
        return ExpandRoundCardViewHolder(parent, scrapChangedListener)
    }

    fun setOnScrapChangedListener(listener: OnScrapChangedCallback) {
        this.scrapChangedListener = listener
    }

    interface OnScrapChangedCallback {
        fun onScrapChanged()
    }
}
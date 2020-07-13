package com.stormers.storm.card.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.viewholder.SavedCardViewHolder

class SavedCardAdapter(private val showHeart: Boolean, private val listener: OnCardClickListener?) : BaseAdapter<SavedCardEntity>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SavedCardEntity> {
        return SavedCardViewHolder(parent, showHeart, listener)
    }

    interface OnCardClickListener {
        fun onCardClick(projectIdx: Int, roundIdx: Int)
    }
}
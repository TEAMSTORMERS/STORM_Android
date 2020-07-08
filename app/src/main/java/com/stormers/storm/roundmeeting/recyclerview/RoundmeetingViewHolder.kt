package com.stormers.storm.roundmeeting.recyclerview

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder

class RoundmeetingViewHolder(parent: ViewGroup) : BaseViewHolder<RoundmeetingData>(R.layout.item_roundmeeting, parent) {
    val ImageView_added_card_roundmeeting = itemView.findViewById<ImageView>(R.id.ImageView_added_card_roundmeeting)

    override fun bind(data : RoundmeetingData){
        Glide.with(itemView).load(data.ImageView_added_card_roundmeeting).into(ImageView_added_card_roundmeeting)
    }

}//날림
package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.round.model.FinalRoundData

class RoundUserImageViewHolder(parent: ViewGroup) : BaseViewHolder<FinalRoundData>(R.layout.item_user_profile, parent){

    val round_user_image_list = itemView.findViewById<ImageView>(R.id.imageview_user_profile)
    override fun bind(data: FinalRoundData) {
        Glide.with(itemView).load(data).into(round_user_image_list)
    }
}
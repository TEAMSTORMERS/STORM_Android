package com.stormers.storm.roundmeeting.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.addcard.recyclerview.AddedCardData

class RoundmeetingViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ImageView_added_card_roundmeeting = itemView.findViewById<ImageView>(R.id.ImageView_added_card_roundmeeting)


    fun bind(RoundmeetingData : RoundmeetingData){
        Glide.with(itemView).load(RoundmeetingData.ImageView_added_card_roundmeeting).into(ImageView_added_card_roundmeeting)
    }

}
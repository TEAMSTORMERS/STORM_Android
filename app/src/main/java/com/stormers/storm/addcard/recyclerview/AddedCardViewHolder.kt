package com.stormers.storm.addcard.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormers.storm.R

class AddedCardViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ImageView_added_card = itemView.findViewById<ImageView>(R.id.ImageView_added_card)


    fun bind(addedCardData : AddedCardData){
        Glide.with(itemView).load(addedCardData.ImageView_added_card).into(ImageView_added_card)
    }

}
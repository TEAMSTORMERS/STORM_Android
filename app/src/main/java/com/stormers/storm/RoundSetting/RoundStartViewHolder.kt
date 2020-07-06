package com.stormers.storm.RoundSetting

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormers.storm.R

class RoundStartViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
    val profile_image = itemView.findViewById<ImageView>(R.id.profile_participant)
    val participant_name = itemView.findViewById<TextView>(R.id.participant_name)

    fun bind(roundStartData: RoundStartData) {
        Glide.with(itemView).load(roundStartData.profile_image).into(profile_image)
        participant_name.text = roundStartData.participants_name
    }
}
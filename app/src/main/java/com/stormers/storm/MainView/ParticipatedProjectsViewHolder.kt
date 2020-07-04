package com.stormers.storm.MainView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stormers.storm.R

class ParticipatedProjectsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val name_of_project = itemView.findViewById<TextView>(R.id.name_of_project)
    val card1 = itemView.findViewById<ImageView>(R.id.card1)
    val card2 = itemView.findViewById<ImageView>(R.id.card2)
    val card3 = itemView.findViewById<ImageView>(R.id.card3)
    val card4 = itemView.findViewById<ImageView>(R.id.card4)

    fun bind(participatedProjectsData: ParticipatedProjectsData){
        Glide.with(itemView).load(participatedProjectsData.card1).into(card1)
        Glide.with(itemView).load(participatedProjectsData.card2).into(card2)
        Glide.with(itemView).load(participatedProjectsData.card3).into(card3)
        Glide.with(itemView).load(participatedProjectsData.card4).into(card4)
        name_of_project.text = participatedProjectsData.name_of_project

    }
}
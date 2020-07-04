package com.stormers.storm

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ParticipatedProjectsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val project_img = itemView.findViewById<ImageView>(R.id.project_img)
    val name_of_project = itemView.findViewById<TextView>(R.id.name_of_project)

    fun bind(participatedProjectsData:ParticipatedProjectsData){
        Glide.with(itemView).load(participatedProjectsData.project_img).into(project_img)
        name_of_project.text = participatedProjectsData.name_of_project

    }
}
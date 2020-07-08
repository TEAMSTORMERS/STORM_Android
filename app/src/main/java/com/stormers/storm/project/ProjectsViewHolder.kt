package com.stormers.storm.project

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder

class ProjectsViewHolder(parent: ViewGroup) : BaseViewHolder<ProjectsData>(R.layout.item_participated_projects_list, parent) {

    private val nameOfProject = itemView.findViewById<TextView>(R.id.name_of_project)
    private val card1 = itemView.findViewById<ImageView>(R.id.card1)
    private val card2 = itemView.findViewById<ImageView>(R.id.card2)
    private val card3 = itemView.findViewById<ImageView>(R.id.card3)
    private val card4 = itemView.findViewById<ImageView>(R.id.card4)

    override fun bind(data: ProjectsData) {
        Glide.with(itemView).load(data.card1).into(card1)
        Glide.with(itemView).load(data.card2).into(card2)
        Glide.with(itemView).load(data.card3).into(card3)
        Glide.with(itemView).load(data.card4).into(card4)
        nameOfProject.text = data.name_of_project
    }
}
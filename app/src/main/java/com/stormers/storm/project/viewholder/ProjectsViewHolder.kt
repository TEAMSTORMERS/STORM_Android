package com.stormers.storm.project.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.model.RecentProjectsModel
import com.stormers.storm.ui.MainActivity

class ProjectsViewHolder(parent: ViewGroup, val listener:MainActivity.OnRoundClickListener?) : BaseViewHolder<RecentProjectsModel>(R.layout.item_participated_projects_list, parent) {

    private val nameOfProject = itemView.findViewById<TextView>(R.id.name_of_project)
    private val card1 = itemView.findViewById<ImageView>(R.id.card1)
    private val card2 = itemView.findViewById<ImageView>(R.id.card2)
    private val card3 = itemView.findViewById<ImageView>(R.id.card3)
    private val card4 = itemView.findViewById<ImageView>(R.id.card4)

    override fun bind(data: RecentProjectsModel) {
        Glide.with(itemView).load(data.card1).into(card1)
        Glide.with(itemView).load(data.card2).into(card2)
        Glide.with(itemView).load(data.card3).into(card3)
        Glide.with(itemView).load(data.card4).into(card4)
        nameOfProject.text = data.projectName

        listener?.let{
            itemView.setOnClickListener{
                data.project_idx{
                    it1 ->listener.onRoundClick(it1)
                }
            }
        }
    }
}
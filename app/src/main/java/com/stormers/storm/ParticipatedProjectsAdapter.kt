package com.stormers.storm

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

class ParticipatedProjectsAdapter (private val context: Context) : RecyclerView.Adapter<ParticipatedProjectsViewHolder>(){
    var datas = mutableListOf<ParticipatedProjectsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipatedProjectsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_participated_projects_list,parent,false)
        return ParticipatedProjectsViewHolder(view)
    }
    override fun getItemCount() : Int{
        return datas.size
    }

    override fun onBindViewHolder(holder: ParticipatedProjectsViewHolder, position: Int) {
        holder.bind(datas[position])

    }
}
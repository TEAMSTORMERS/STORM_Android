package com.stormers.storm.MainView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R

class ParticipatedProjectsAdapter (private val context: Context) : RecyclerView.Adapter<ParticipatedProjectsViewHolder>(){
    var datas = mutableListOf<ParticipatedProjectsData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParticipatedProjectsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_participated_projects_list, parent,false)
        return ParticipatedProjectsViewHolder(parent)
    }
    override fun getItemCount() : Int{
        return datas.size
    }

    override fun onBindViewHolder(holder: ParticipatedProjectsViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
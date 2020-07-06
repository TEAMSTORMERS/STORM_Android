package com.stormers.storm.RoundSetting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R

class RoundStartAdapter (private val context: Context) :RecyclerView.Adapter<RoundStartViewHolder>(){
    var datas = mutableListOf<RoundStartData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundStartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_participants_list, parent,false)
        return RoundStartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RoundStartViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
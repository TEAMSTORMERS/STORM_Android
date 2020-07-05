package com.stormers.storm.roundmeeting.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.addcard.recyclerview.AddedCardData
import com.stormers.storm.addcard.recyclerview.AddedCardViewHolder

class RoundmeetingAdapter (private val context : Context) : RecyclerView.Adapter<RoundmeetingViewHolder>() {
    var datas = mutableListOf<RoundmeetingData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundmeetingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_roundmeeting, parent, false)
        return RoundmeetingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RoundmeetingViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
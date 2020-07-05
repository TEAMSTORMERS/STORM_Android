package com.stormers.storm.addcard.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R

class AddedCardAdapter (private val context : Context) : RecyclerView.Adapter<AddedCardViewHolder>() {
    var datas = mutableListOf<AddedCardData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_added_card, parent, false)
        return AddedCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: AddedCardViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}
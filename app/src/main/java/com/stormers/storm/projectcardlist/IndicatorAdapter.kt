package com.stormers.storm.projectcardlist

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.projectcardlist.recyclerview.RoundDescriptionModel
import kotlinx.android.synthetic.main.item_round_info_card.view.*

class IndicatorAdapter (val context: Context, var cardList: List<RoundDescriptionModel>)
    : RecyclerView.Adapter<IndicatorAdapter.IndexListViewHolder>() {
    val indexList = Array(cardList.size) { false } // 리스트를 모두 false로 초기화
    var index = 0 // 선택된 item의 index

    init {
        if (cardList.isNotEmpty())
            indexList[0] = true // 첫번째 요소를 default로 선택
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): IndexListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_round_info_card, parent, false)
        return IndexListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setItemIndex(position: Int) {
        // index를 새로운 item의 position으로 교체
        indexList[index] = false
        indexList[position] = true
        index = position

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: IndexListViewHolder, position: Int) {
        val asset = context.resources.getDrawable(
            if (indexList[position]) R.drawable.fill_circle
            else R.drawable.stroke_circle
        )

        holder.bind(asset)
    }

    inner class IndexListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dotsView: View = itemView.item_card

        fun bind(asset: Drawable) {
            dotsView.background = asset
        }
    }
}
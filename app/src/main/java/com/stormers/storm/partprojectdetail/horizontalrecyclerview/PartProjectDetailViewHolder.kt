package com.stormers.storm.partprojectdetail.horizontalrecyclerview

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder

class PartProjectDetailViewHolder (parent: ViewGroup) : BaseViewHolder<PartProjectDetailData>(R.layout.item_scrap_card, parent) {
    val imageView_scrap_card = itemView.findViewById<ImageView>(R.id.imageView_scrap_card)

    override fun bind(data : PartProjectDetailData){
        Glide.with(itemView).load(data.imageView_scrap_card).into(imageView_scrap_card)
    }

}
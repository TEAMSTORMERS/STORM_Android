package com.stormers.storm.scrapcollect.recyclerview

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder

class ScrapCollectViewHolder(parent: ViewGroup) : BaseViewHolder<ScrapCollectData>(R.layout.item_scrap_collect, parent) {
    val imageView_scrap_collect = itemView.findViewById<ImageView>(R.id.imageView_scrap_collect)

    override fun bind(data : ScrapCollectData){
        Glide.with(itemView).load(data.imageView_scrap_collect).into(imageView_scrap_collect)
    }
//날림
}
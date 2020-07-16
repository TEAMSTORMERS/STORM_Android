package com.stormers.storm.card.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.item_expandcard_card.view.*

class ExpandCardViewHolder(parent: ViewGroup) : BaseViewHolder<SavedCardEntity>(R.layout.item_expandcard_card, parent) {

    override fun bind(data: SavedCardEntity) {

        //배경 둥글게 자르기
        itemView.imageview_expandcard_profile.run {
            background = ShapeDrawable(OvalShape())
            clipToOutline = true
        }

        if (data.isScraped == SavedCardEntity.TRUE) {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
        } else {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.h_roundmeeting_btn_heart4)
        }

        if (data.cardType == SavedCardEntity.DRAWING) {
            Glide.with(itemView).load(data.content).into(itemView.imageview_expandcard_content)
            itemView.textview_expandcard_content.visibility = View.INVISIBLE
        } else {
            itemView.textview_expandcard_content.text = data.content
            itemView.imageview_expandcard_content.visibility = View.INVISIBLE
        }
    }
}

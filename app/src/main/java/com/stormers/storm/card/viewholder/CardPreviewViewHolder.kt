package com.stormers.storm.card.viewholder

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardPreviewModel
import kotlinx.android.synthetic.main.item_card_preview.view.*

class CardPreviewViewHolder(parent: ViewGroup) : BaseViewHolder<CardPreviewModel>(R.layout.item_card_preview, parent) {

    override fun bind(data: CardPreviewModel) {
        if (data.cardIdx == -1) {
            itemView.imageview_cardpreview_img.visibility = View.VISIBLE
            itemView.imageview_cardpreview_img.setImageResource(R.drawable.h_roundstart_popup_symbol)
            itemView.textview_cardpreview_txt.visibility = View.GONE
        } else {
            if (data.cardImage != null) {
                itemView.imageview_cardpreview_img.visibility = View.VISIBLE
                Glide.with(itemView).load(data).into(itemView.imageview_cardpreview_img)
                itemView.textview_cardpreview_txt.visibility = View.GONE
            } else {
                itemView.textview_cardpreview_txt.visibility = View.VISIBLE
                itemView.textview_cardpreview_txt.text = data.cardText
                itemView.imageview_cardpreview_img.visibility = View.GONE
            }
        }
    }
}
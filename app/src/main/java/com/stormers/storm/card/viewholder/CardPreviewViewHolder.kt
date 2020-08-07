package com.stormers.storm.card.viewholder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.SimpleCardModel
import kotlinx.android.synthetic.main.item_card_preview.view.*

class CardPreviewViewHolder(parent: ViewGroup) : BaseViewHolder<SimpleCardModel>(R.layout.item_card_preview, parent) {

    override fun bind(data: SimpleCardModel) {
        data.card_img?.let {
            itemView.imageview_cardpreview_img.visibility = View.VISIBLE

            if (it == "null") {
                itemView.imageview_cardpreview_img.setImageResource(R.drawable.h_roundstart_popup_symbol)
            } else {
                Glide.with(itemView).load(it).into(itemView.imageview_cardpreview_img)
            }

            itemView.textview_cardpreview_txt.visibility = View.GONE
        }

        data.card_txt?.let {
            itemView.textview_cardpreview_txt.visibility = View.VISIBLE
            itemView.textview_cardpreview_txt.text = it
            itemView.imageview_cardpreview_img.visibility = View.GONE
        }
    }
}
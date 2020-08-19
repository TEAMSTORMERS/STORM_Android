package com.stormers.storm.card.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.card.repository.CardRepository
import kotlinx.android.synthetic.main.item_expandcard_card.view.*

class ExpandCardViewHolder(parent: ViewGroup) : BaseViewHolder<CardEntity>(R.layout.item_expandcard_card, parent) {

    private val cardRepository: CardRepository by lazy { CardRepository() }

    private var isScraped = false

    override fun bind(data: CardEntity) {

        //배경 둥글게 자르기
        itemView.imageview_expandcard_profile.run {
            background = ShapeDrawable(OvalShape())
            clipToOutline = true
        }

        isScraped = data.isScraped == CardEntity.TRUE

        applyHeart(isScraped, data)

        itemView.imagebutton_expandcard_heart.setOnClickListener {
            switchHeart(data)
        }

        if (data.cardType == CardEntity.DRAWING) {
            Glide.with(itemView).load(data.content).into(itemView.imageview_expandcard_content)
            itemView.textview_expandcard_content.visibility = View.INVISIBLE
        } else {
            itemView.textview_expandcard_content.text = data.content
            itemView.imageview_expandcard_content.visibility = View.INVISIBLE
        }
    }

    private fun switchHeart(data: CardEntity) {
        isScraped = !isScraped

        applyHeart(isScraped, data)
    }

    private fun applyHeart(isScraped: Boolean, data: CardEntity) {
        if (isScraped) {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
            data.isScraped = CardEntity.TRUE
        } else {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.h_roundmeeting_btn_heart4)
            data.isScraped = CardEntity.FALSE
        }
        cardRepository.update(data)
    }
}

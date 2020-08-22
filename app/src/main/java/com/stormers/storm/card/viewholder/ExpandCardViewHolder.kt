package com.stormers.storm.card.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.CardType
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.repository.CardRepository
import kotlinx.android.synthetic.main.item_expandcard_card.view.*

class ExpandCardViewHolder(parent: ViewGroup) : BaseViewHolder<CardModel>(R.layout.item_expandcard_card, parent) {

    private val cardRepository: CardRepository by lazy { CardRepository() }

    override fun bind(data: CardModel) {

        //카드 소유자 프로필 나타내기
        itemView.imageview_expandcard_profile.run {
            background = ShapeDrawable(OvalShape())
            clipToOutline = true
            Glide.with(itemView).load(data.cardOwner.userImg).into(this)
        }

        //하트 유무 적용
        applyHeart(data.isScraped)

        //하트 리스너 초기화
        itemView.imagebutton_expandcard_heart.setOnClickListener {
            switchHeart(data)
        }

        //카드 컨텐츠 초기화
        if (data.cardType == CardType.DRAWING) {
            Glide.with(itemView).load(data.cardContent).into(itemView.imageview_expandcard_content)
            itemView.textview_expandcard_content.visibility = View.INVISIBLE
        } else {
            itemView.textview_expandcard_content.text = data.cardContent
            itemView.imageview_expandcard_content.visibility = View.INVISIBLE
        }
    }

    private fun switchHeart(data: CardModel) {
        data.run {
            isScraped = !isScraped
            applyHeart(isScraped)
            cardRepository.update(this)
        }
    }

    private fun applyHeart(isScraped: Boolean) {
        if (isScraped) {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
        } else {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.h_roundmeeting_btn_heart4)
        }
    }
}

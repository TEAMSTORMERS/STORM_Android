package com.stormers.storm.card

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder

class CardDetailViewHolder(parent: ViewGroup) : BaseViewHolder<CardModel>(R.layout.item_card_detail, parent) {
    private val imageViewCardDetailBackground = itemView.findViewById<ImageView>(R.id.imageview_carddetail_background)
    private val imageViewCardDetailProfile = itemView.findViewById<ImageView>(R.id.imageview_carddetail_profile)
    private val imageButtonCardDetailHeart = itemView.findViewById<ImageButton>(R.id.imagebutton_carddetail_heart)

    override fun bind(data: CardModel) {
        //배경 둥글게 자르기
        imageViewCardDetailProfile.background = ShapeDrawable(OvalShape())
        imageViewCardDetailProfile.clipToOutline = true


        Glide.with(itemView).load(data.url).into(imageViewCardDetailBackground)
        Glide.with(itemView).load(data.owner?.profileUrl).into(imageViewCardDetailProfile)
        if (data.isLiked) {
            imageButtonCardDetailHeart.setImageResource(R.drawable.scrapcard_btn_heart_1)
        } else {
            imageButtonCardDetailHeart.setImageResource(R.drawable.h_roundmeeting_btn_heart4)
        }
    }

}

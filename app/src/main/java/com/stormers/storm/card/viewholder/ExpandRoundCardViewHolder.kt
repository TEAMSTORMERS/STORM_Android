package com.stormers.storm.card.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.CardWithOwnerModel
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.item_expandcard_card.view.*

class ExpandRoundCardViewHolder(parent: ViewGroup) : BaseExpandCardViewHolder<CardWithOwnerModel>(parent) {

    override fun onCreateCardView(data: CardWithOwnerModel) {
        if (data.cardImage != null) {
            setImageUrl(data.cardImage)
        } else {
            setCardText(data.cardText!!)
        }
    }

    override fun onClickHeart(data: CardWithOwnerModel): Boolean {
        data.run {
            isScraped = if (isScraped == 1) {
                unScrapCard(data.cardIdx)
                0
            } else {
                scrapCard(data.cardIdx)
                1
            }

            return isScraped == 1
        }
    }

    override fun onOwnerImageAttached(data: CardWithOwnerModel): String {
        return data.userImg
    }
}

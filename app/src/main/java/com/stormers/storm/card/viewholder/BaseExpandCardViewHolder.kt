package com.stormers.storm.card.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.ScrapedCardRelationModel
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.item_expandcard_card.view.*

abstract class BaseExpandCardViewHolder<T>(parent: ViewGroup) : BaseViewHolder<T>(R.layout.item_expandcard_card, parent) {

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(
        CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    override fun bind(data: T) {

        onCreateCardView(data)

        itemView.imageview_expandcard_profile.run {
            background = ShapeDrawable(OvalShape())
            clipToOutline = true

            Glide.with(itemView).load(onOwnerImageAttached(data)).circleCrop().into(this)
        }

        itemView.imagebutton_expandcard_heart.setOnClickListener {
            applyHeart(onClickHeart(data))
        }
    }

    abstract fun onClickHeart(data: T) : Boolean

    abstract fun onOwnerImageAttached(data: T) : String

    abstract fun onCreateCardView(data: T)

    protected fun applyHeart(isScraped: Boolean) {
        if (isScraped) {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
        } else {
            itemView.imagebutton_expandcard_heart.setImageResource(R.drawable.h_roundmeeting_btn_heart4)
        }
    }

    protected fun scrapCard(cardIdx: Int) {
        cardRepository.scrapCard(ScrapedCardRelationModel(GlobalApplication.userIdx, cardIdx))
    }

    protected fun unScrapCard(cardIdx: Int) {
        cardRepository.unScrapCard(ScrapedCardRelationModel(GlobalApplication.userIdx, cardIdx))
    }

    protected fun setImageUrl(imageUrl: String) {
        Glide.with(itemView).load(imageUrl).into(itemView.imageview_expandcard_content)
        itemView.textview_expandcard_content.visibility = View.INVISIBLE
    }

    protected fun setCardText(text: String) {
        itemView.textview_expandcard_content.text = text
        itemView.imageview_expandcard_content.visibility = View.INVISIBLE
    }
}
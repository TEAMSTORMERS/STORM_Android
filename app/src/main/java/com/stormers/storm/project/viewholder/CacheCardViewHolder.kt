package com.stormers.storm.project.viewholder

import android.util.Log
import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.item_heart_card.view.*

class CacheCardViewHolder(parent: ViewGroup) : BaseViewHolder<CacheCardModel>(R.layout.item_heart_card, parent) {

    private val TAG = "CacheCardViewHolder"

    override fun bind(data: CacheCardModel) {
        itemView.stormcard_itemheart.run {
            showHeartButton(false)

            if (data.isImage) {
                BitmapConverter.stringToBitmap(data.content)?.let {
                    setBitmap(it)
                } ?: Log.d(TAG, "bind bitmap: fail. ${data.content}")
            } else {
                setText(data.content)
            }
        }
    }
}

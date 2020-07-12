package com.stormers.storm.card.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.ui.ParticipatedProjectDetailActivity
import kotlinx.android.synthetic.main.item_heart_card.view.*

//이제 이 뷰 홀더는 리스너를 필수 파라미터로 필요로 하게 돼. 하지만 필요 없을 수도 있으니까 null 허용
class NoHeartCardViewHolder(parent: ViewGroup, val listener: ParticipatedProjectDetailActivity.OnRoundClickListener?) : BaseViewHolder<CardModel>(R.layout.item_heart_card, parent) {

    override fun bind(data: CardModel) {
        itemView.stormcard_itemheart.showHeartButton(false)
        itemView.stormcard_itemheart.setImageUrl(data.url)
        
        itemView.stormcard_itemheart.heartState = data.isLiked

        //넘어온 리스너가 있으면 적용
        listener?.let {
            itemView.setOnClickListener {
                //Todo: CardModel을 개편해야겠다 ~ projectIdx, roundIdx를 포함하고 있어야겠어
                //it.onRoundClick(data.roundIdx)
            }
        }

    }
}
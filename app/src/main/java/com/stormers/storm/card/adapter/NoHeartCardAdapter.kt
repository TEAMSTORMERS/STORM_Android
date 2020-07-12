package com.stormers.storm.card.adapter

import android.content.Context
import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.viewholder.NoHeartCardViewHolder
import com.stormers.storm.ui.ParticipatedProjectDetailActivity

//이제 이 어댑터는 리스너를 필수 파라미터로 가지는데 필요 없을 수도 있으니 null 가능
class NoHeartCardAdapter(private val listener: ParticipatedProjectDetailActivity.OnRoundClickListener?) : BaseAdapter<CardModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CardModel> {

        //뷰홀더에 전달하기
        return NoHeartCardViewHolder(parent, listener)
    }
}
package com.stormers.storm

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.base.BaseViewHolder

class ParticipantViewHolder(parent: ViewGroup): BaseViewHolder<UserModel>(R.layout.item_participant_with_profile, parent) {
    private val imageViewParticipantItemProfile = itemView.findViewById<ImageView>(R.id.imageview_participantitem_profile)
    private val textViewParticipantItemName = itemView.findViewById<TextView>(R.id.textview_participantitem_name)

    override fun bind(data: UserModel) {
        //배경 둥글게 자르기
        imageViewParticipantItemProfile.background = ShapeDrawable(OvalShape())
        imageViewParticipantItemProfile.clipToOutline = true

        //프로필 사진 적용
        Glide.with(itemView).load(data.profileUrl).into(imageViewParticipantItemProfile)

        //이름 적용
        textViewParticipantItemName.text = data.name
    }
}
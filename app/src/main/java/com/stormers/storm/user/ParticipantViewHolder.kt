package com.stormers.storm.user

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import java.lang.StringBuilder

class ParticipantViewHolder(parent: ViewGroup): BaseViewHolder<User>(R.layout.item_participant_with_profile, parent) {
    private val imageViewParticipantItemProfile = itemView.findViewById<ImageView>(
        R.id.imageview_participantitem_profile
    )
    private val textViewParticipantItemName = itemView.findViewById<TextView>(R.id.textview_participantitem_name)

    override fun bind(data: User) {
        //배경 둥글게 자르기
        imageViewParticipantItemProfile.background = ShapeDrawable(OvalShape())
        imageViewParticipantItemProfile.clipToOutline = true

        //프로필 사진 적용
        Glide.with(itemView).load(data.userImg).into(imageViewParticipantItemProfile)

        //이름 적용
        val name = StringBuilder(data.userName)

        //호스트라면
        if (data.isHost == 1) {
            name.append(" (HOST)")
        }
        //반영
        textViewParticipantItemName.text = name.toString()
    }
}
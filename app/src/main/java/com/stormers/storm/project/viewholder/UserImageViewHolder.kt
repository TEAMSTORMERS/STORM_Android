package com.stormers.storm.project.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.user.User

class UserImageViewHolder (parent:ViewGroup) : BaseViewHolder<String>(R.layout.item_user_profile, parent){

    private val imageViewUserProfile = itemView.findViewById<ImageView>(R.id.imageview_user_profile)

    override fun bind(data: String) {

        imageViewUserProfile.background = ShapeDrawable(OvalShape())
        imageViewUserProfile.clipToOutline = true

        Glide.with(itemView).load(data).into(imageViewUserProfile)

    }
}

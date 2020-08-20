package com.stormers.storm.project.viewholder

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.user.UserModel

class UserImageViewHolder (parent:ViewGroup) : BaseViewHolder<UserModel>(R.layout.item_user_profile, parent){

    private val imageViewUserProfile = itemView.findViewById<ImageView>(R.id.imageview_user_profile)

    override fun bind(data: UserModel) {

        imageViewUserProfile.background = ShapeDrawable(OvalShape())
        imageViewUserProfile.clipToOutline = true

        Glide.with(itemView).load(data.userImg).into(imageViewUserProfile)

    }
}

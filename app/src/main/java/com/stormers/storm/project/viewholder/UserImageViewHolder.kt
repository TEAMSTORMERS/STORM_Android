package com.stormers.storm.project.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.model.ProjectInfoForUserImageModel

class UserImageViewHolder (parent:ViewGroup) : BaseViewHolder<String>(R.layout.activity_participated_project_detail, parent){

    val imageview_user_profile = itemView.findViewById<ImageView>(R.id.imageview_user_profile)

    override fun bind(data: String) {
        Glide.with(itemView).load(data).into(imageview_user_profile)
    }
}

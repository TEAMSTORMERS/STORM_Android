package com.stormers.storm.project.viewholder

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

class ParticipatedProjectViewHolder(parent: ViewGroup, private val isMain: Boolean, private val listener: ParticipatedProjectListAdapter.OnProjectClickListener) :
    BaseViewHolder<ParticipatedProjectModel>(R.layout.item_participated_projects_list, parent) {

    override fun bind(data: ParticipatedProjectModel) {
        initItemLayout(data)

        initCardImage(data)
    }

    private fun initItemLayout(data: ParticipatedProjectModel) {
        itemView.name_of_project.text = data.projectName

        if (!isMain) {
            (itemView.cardview_project_forder.layoutParams).width =
                MetricsUtil.convertDpToPixel(162f, itemView.context).toInt()
            (itemView.cardview_project_forder.layoutParams).height =
                MetricsUtil.convertDpToPixel(174f, itemView.context).toInt()
        }

        itemView.setOnClickListener {
            listener.onProjectClick(data.projectIdx)
        }
    }

    private fun initCardImage(data: ParticipatedProjectModel) {
        when (data.projectCard.size) {
            0 -> return
            1 -> {
                if (data.projectCard[0].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[0].card_img).into(itemView.card_img1)
                    itemView.card_txt1.visibility = View.GONE
                } else {
                    itemView.card_txt1.text = data.projectCard[0].card_txt
                    itemView.card_img1.visibility = View.GONE
                }
            }
            2 -> {
                if (data.projectCard[0].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[0].card_img).into(itemView.card_img1)
                    itemView.card_txt1.visibility = View.GONE
                } else {
                    itemView.card_txt1.text = data.projectCard[0].card_txt
                    itemView.card_img1.visibility = View.GONE
                }

                if (data.projectCard[1].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[1].card_img).into(itemView.card_img2)
                    itemView.card_txt2.visibility = View.GONE
                } else {
                    itemView.card_txt2.text = data.projectCard[1].card_txt
                    itemView.card_img2.visibility = View.GONE
                }
            }
            3 -> {
                if (data.projectCard[0].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[0].card_img).into(itemView.card_img1)
                    itemView.card_txt1.visibility = View.GONE
                } else {
                    itemView.card_txt1.text = data.projectCard[0].card_txt
                    itemView.card_img1.visibility = View.GONE
                }

                if (data.projectCard[1].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[1].card_img).into(itemView.card_img2)
                    itemView.card_txt2.visibility = View.GONE
                } else {
                    itemView.card_txt2.text = data.projectCard[1].card_txt
                    itemView.card_img2.visibility = View.GONE
                }

                if (data.projectCard[2].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[2].card_img).into(itemView.card_img3)
                    itemView.card_txt3.visibility = View.GONE
                } else {
                    itemView.card_txt3.text = data.projectCard[2].card_txt
                    itemView.card_img3.visibility = View.GONE
                }
            }
            else -> {
                if (data.projectCard[0].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[0].card_img).into(itemView.card_img1)
                    itemView.card_txt1.visibility = View.GONE
                } else {
                    itemView.card_txt1.text = data.projectCard[0].card_txt
                    itemView.card_img1.visibility = View.GONE
                }

                if (data.projectCard[1].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[1].card_img).into(itemView.card_img2)
                    itemView.card_txt2.visibility = View.GONE
                } else {
                    itemView.card_txt2.text = data.projectCard[1].card_txt
                    itemView.card_img2.visibility = View.GONE
                }

                if (data.projectCard[2].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[2].card_img).into(itemView.card_img3)
                    itemView.card_txt3.visibility = View.GONE
                } else {
                    itemView.card_txt3.text = data.projectCard[2].card_txt
                    itemView.card_img3.visibility = View.GONE
                }

                if (data.projectCard[3].card_img != null) {
                    Glide.with(itemView).load(data.projectCard[3].card_img).into(itemView.card_img4)
                    itemView.card_txt4.visibility = View.GONE
                } else {
                    itemView.card_txt4.text = data.projectCard[3].card_txt
                    itemView.card_img4.visibility = View.GONE
                }
            }
        }
    }
}
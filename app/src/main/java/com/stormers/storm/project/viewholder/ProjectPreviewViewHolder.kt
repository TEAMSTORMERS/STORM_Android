package com.stormers.storm.project.viewholder

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardPreviewModel
import com.stormers.storm.project.adapter.ProjectPreviewAdapter
import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

class ProjectPreviewViewHolder(parent: ViewGroup, private val isMain: Boolean, private val listener: ProjectPreviewAdapter.OnProjectClickListener) :
    BaseViewHolder<ProjectPreviewModel>(R.layout.item_participated_projects_list, parent) {

    override fun bind(data: ProjectPreviewModel) {
        initItemLayout(data)

        initPreview(getPreviewCards(data))
    }

    private fun initItemLayout(data: ProjectPreviewModel) {
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

    private fun getPreviewCards(data: ProjectPreviewModel): List<CardPreviewModel> {
        val cardData = mutableListOf<CardPreviewModel>()
        for (i in 0 until 4) {
            if (i < data.projectCardPreview.size) {
                cardData.add(data.projectCardPreview[i])
            } else {
                cardData.add(CardPreviewModel(-1, null, null))
            }
        }
        return cardData
    }

    private fun initPreview(data: List<CardPreviewModel>) {
        itemView.run {
            if (data[0].cardIdx == -1) {
                imageview_cardpreview_img1.visibility = View.VISIBLE
                imageview_cardpreview_img1.setImageResource(R.drawable.h_roundstart_popup_symbol)
                textview_cardpreview_txt1.visibility = View.GONE
            } else {
                if (data[0].cardImage != null) {
                    imageview_cardpreview_img1.visibility = View.VISIBLE
                    Glide.with(this).load(data[0].cardImage).into(imageview_cardpreview_img1)
                    textview_cardpreview_txt1.visibility = View.GONE
                } else {
                    textview_cardpreview_txt1.visibility = View.VISIBLE
                    textview_cardpreview_txt1.text = data[0].cardText
                    imageview_cardpreview_img1.visibility = View.GONE
                }
            }

            if (data[1].cardIdx == -1) {
                imageview_cardpreview_img2.visibility = View.VISIBLE
                imageview_cardpreview_img2.setImageResource(R.drawable.h_roundstart_popup_symbol)
                textview_cardpreview_txt2.visibility = View.GONE
            } else {
                if (data[1].cardImage != null) {
                    imageview_cardpreview_img2.visibility = View.VISIBLE
                    Glide.with(this).load(data[1].cardImage).into(imageview_cardpreview_img2)
                    textview_cardpreview_txt2.visibility = View.GONE
                } else {
                    textview_cardpreview_txt2.visibility = View.VISIBLE
                    textview_cardpreview_txt2.text = data[1].cardText
                    imageview_cardpreview_img2.visibility = View.GONE
                }
            }

            if (data[2].cardIdx == -1) {
                imageview_cardpreview_img3.visibility = View.VISIBLE
                imageview_cardpreview_img3.setImageResource(R.drawable.h_roundstart_popup_symbol)
                textview_cardpreview_txt3.visibility = View.GONE
            } else {
                if (data[2].cardImage != null) {
                    imageview_cardpreview_img3.visibility = View.VISIBLE
                    Glide.with(this).load(data[2].cardImage).into(imageview_cardpreview_img3)
                    textview_cardpreview_txt3.visibility = View.GONE
                } else {
                    textview_cardpreview_txt3.visibility = View.VISIBLE
                    textview_cardpreview_txt3.text = data[2].cardText
                    imageview_cardpreview_img3.visibility = View.GONE
                }
            }

            if (data[3].cardIdx == -1) {
                imageview_cardpreview_img4.visibility = View.VISIBLE
                imageview_cardpreview_img4.setImageResource(R.drawable.h_roundstart_popup_symbol)
                textview_cardpreview_txt4.visibility = View.GONE
            } else {
                if (data[3].cardImage != null) {
                    imageview_cardpreview_img4.visibility = View.VISIBLE
                    Glide.with(this).load(data[3].cardImage).into(imageview_cardpreview_img3)
                    textview_cardpreview_txt4.visibility = View.GONE
                } else {
                    textview_cardpreview_txt4.visibility = View.VISIBLE
                    textview_cardpreview_txt4.text = data[3].cardText
                    imageview_cardpreview_img4.visibility = View.GONE
                }
            }
        }
    }
}
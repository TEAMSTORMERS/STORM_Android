package com.stormers.storm.project.viewholder

import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.adapter.CardPreviewAdapter
import com.stormers.storm.card.model.SimpleCardModel
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

class ParticipatedProjectViewHolder(parent: ViewGroup, private val isMain: Boolean, private val listener: ParticipatedProjectListAdapter.OnProjectClickListener) :
    BaseViewHolder<ParticipatedProjectModel>(R.layout.item_participated_projects_list, parent) {

    private val cardPreviewAdapter: CardPreviewAdapter by lazy { CardPreviewAdapter() }

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
        itemView.recyclerview_projectfolder.run {
            adapter = cardPreviewAdapter
        }
        for (i in 0 until 4) {
            if (i < data.projectCard.size) {
                cardPreviewAdapter.add(data.projectCard[i])
            } else {
                cardPreviewAdapter.add(SimpleCardModel("null", null))
            }
        }
    }
}
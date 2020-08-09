package com.stormers.storm.project.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.adapter.CardPreviewAdapter
import com.stormers.storm.card.model.SimpleCardModel
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.util.MetricsUtil
import com.stormers.storm.util.MiddleDividerItemDecoration
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
        itemView.recyclerview_projectfolder.recyclerView.run {
            adapter = cardPreviewAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(MiddleDividerItemDecoration(context, MiddleDividerItemDecoration.VERTICAL))
            addItemDecoration(MiddleDividerItemDecoration(context, MiddleDividerItemDecoration.HORIZONTAL))
        }

        val cardData = mutableListOf<SimpleCardModel>()
        for (i in 0 until 4) {
            if (i < data.projectCard.size) {
                cardData.add(data.projectCard[i])
            } else {
                cardData.add(SimpleCardModel("null", null))
            }
        }
        cardPreviewAdapter.setList(cardData)
    }
}
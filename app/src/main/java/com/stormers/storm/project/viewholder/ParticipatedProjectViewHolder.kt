package com.stormers.storm.project.viewholder

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.ui.ParticipatedProjectListActivity
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

/**
 * 어댑터를 생성할 때 전달한 isMain이랑 listener를 여기까지 겨우 겨우 끌고 왔어
 * 이제 이 파라미터를 뷰홀더, 즉 각 아이템에 적용하면 돼
 */
class ParticipatedProjectViewHolder(parent: ViewGroup, private val isMain: Boolean, private val listener: ParticipatedProjectListAdapter.OnProjectClickListener) :
    BaseViewHolder<ParticipatedProjectModel>(R.layout.item_participated_projects_list, parent) {

    override fun bind(data: ParticipatedProjectModel) {
        initItemLayout(data)

        initCardImage(data)
    }

    private fun initItemLayout(data: ParticipatedProjectModel) {
        itemView.name_of_project.text = data.projectName

        /**
         * 아까 말한대로, isMain 이 false이면, 즉 ParticipatedProjectListActivity에서 어댑터를 생성했다면 item의 크기를 쫌 늘려야해서
         * 아래 코드를 작성하게 됐어. 메인액티비티에서 어댑터를 생성했다면 아래 코드는 실행되지 않아
         */
        if (!isMain) {
            (itemView.cardview_project_forder.layoutParams).width =
                MetricsUtil.convertDpToPixel(162f, itemView.context).toInt()
            (itemView.cardview_project_forder.layoutParams).height =
                MetricsUtil.convertDpToPixel(174f, itemView.context).toInt()
        }

        /**
         * 아까 액티비티에서 실행해주기로 약속한 콜백을 여기까지 가져왔어
         * itemView, 즉 아이템을 클릭하면 아까 약속한 콜백을 실행하게 돼.
         * 이 과정에서 현재 아이템이 보여주고 있는 data(ParticipatedProjectMoel)이 갖고 있는 projectIdx를 넘겨줘
         * 그럼 액티비티에서는 projectIdx를 사용해서 액티비티를 전환하게 돼.
         * 쉽지 !?!? 이게 끝이야 !
         * 주석은 지워줘 !
         */
        itemView.setOnClickListener {
            listener.onProjectClick(data.projectIdx)
        }
    }

    private fun initCardImage(data: ParticipatedProjectModel) {
        when (data.projectCard.size) {
            0 -> return
            1 -> Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
            2 -> {
                Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
                Glide.with(itemView).load(data.projectCard[1]).into(itemView.card2)
            }
            3 -> {
                Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
                Glide.with(itemView).load(data.projectCard[1]).into(itemView.card2)
                Glide.with(itemView).load(data.projectCard[2]).into(itemView.card3)
            }
            else -> {
                Glide.with(itemView).load(data.projectCard[0]).into(itemView.card1)
                Glide.with(itemView).load(data.projectCard[1]).into(itemView.card2)
                Glide.with(itemView).load(data.projectCard[2]).into(itemView.card3)
                Glide.with(itemView).load(data.projectCard[3]).into(itemView.card4)
            }
        }
    }
}
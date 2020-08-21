package com.stormers.storm.round.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ProjectParticipantsAdapter
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.item_round_info_card.view.*
import kotlinx.android.synthetic.main.item_round_part_detail.view.*
import kotlinx.android.synthetic.main.layout_list_user_profile.view.*
import java.lang.StringBuilder

open class BaseRoundViewHolder (parent: ViewGroup, @LayoutRes val layoutRes: Int): BaseViewHolder<RoundModel>(layoutRes, parent) {

    protected var baseItemView = itemView

    private val projectParticipantsAdapter: ProjectParticipantsAdapter by lazy { ProjectParticipantsAdapter() }

    override fun bind(data: RoundModel) {
        if (layoutRes == R.layout.item_round_info_card) {
            baseItemView = itemView.include_roundviewpager_item
        }

        baseItemView.run {
            textview_roundinfo_projectname.visibility = View.GONE
            textview_roundinfo_purpose.text = data.roundPurpose
            textview_roundinfo_roundnumber.text =
                StringBuilder("Round ").append(data.roundNumber).toString()

            textview_roundinfo_purpose.text = data.roundPurpose
            textview_roundinfo_time.text = StringBuilder("총 ").append(data.roundTime).append("분 소요").toString()

            include_roundinfo_participants.recyclerview_user_profile.run {
                addItemDecoration(MarginDecoration(itemView.context, 7, RecyclerView.HORIZONTAL))
                recyclerview_user_profile.adapter = projectParticipantsAdapter

                projectParticipantsAdapter.setList(data.participants!!)

                val numberOfParticipants = data.participants!!.size
                if( numberOfParticipants > 5 ) {
                    textview_extra_participants_info.run {
                        visibility = View.VISIBLE
                        text = StringBuilder("+").append(numberOfParticipants - 5).toString()
                    }
                }
            }
        }
    }
}
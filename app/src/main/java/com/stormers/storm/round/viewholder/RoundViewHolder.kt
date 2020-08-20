package com.stormers.storm.round.viewholder

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ProjectParticipantsAdapter
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.item_round_part_detail.view.*
import kotlinx.android.synthetic.main.layout_list_user_profile.view.*
import java.lang.StringBuilder

class RoundViewHolder (parent: ViewGroup, val projectName: String?, val listener: RoundListAdapter.OnRoundClickListener?) : BaseViewHolder<RoundModel>(R.layout.item_round_part_detail, parent) {

    //항상 액티비티에서 어댑터를 생성했다면 여기서는 뷰홀더에서 어댑터를 생성하기
    private val projectParticipantsAdapter: ProjectParticipantsAdapter by lazy { ProjectParticipantsAdapter() }

    override fun bind(data : RoundModel){

        itemView.run {
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

            projectName?.let {
                textview_roundinfo_projectname.run {
                    visibility = View.VISIBLE
                    text = it
                }
            }
        }

        //유저 이미지 어댑터에 사용자 이미지들의 리스트를 추가해줌

        listener?.let {
            itemView.setOnClickListener {
                listener.onRoundClick(data.roundIdx, data.roundNumber!!)
            }
        }
    }
}



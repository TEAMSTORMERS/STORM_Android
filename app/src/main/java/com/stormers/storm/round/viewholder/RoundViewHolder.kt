package com.stormers.storm.round.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.project.adapter.ProjectUserImageAdapter
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundDescriptionModel
import kotlinx.android.synthetic.main.item_round_part_detail.view.*
import kotlinx.android.synthetic.main.layout_list_user_profile.view.*
import java.lang.StringBuilder

class RoundViewHolder (parent: ViewGroup, val listener: RoundListAdapter.OnRoundClickListener?) : BaseViewHolder<RoundDescriptionModel>(R.layout.item_round_part_detail, parent) {
    val Textview_round_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_roundinfo)
    val Textview_round_goal_roundinfo = itemView.findViewById<TextView>(R.id.Textview_round_goal_roundinfo)
    val Textview_time_roundinfo = itemView.findViewById<TextView>(R.id.Textview_time_roundinfo)

    //항상 액티비티에서 어댑터를 생성했다면 여기서는 뷰홀더에서 어댑터를 생성하기
    private val projectUserImageAdapter: ProjectUserImageAdapter by lazy { ProjectUserImageAdapter() }

    override fun bind(data : RoundDescriptionModel){

        val roundTime = StringBuilder()
        roundTime.append("총 ")
            .append(data.time)
            .append("분 소요")

        Textview_round_roundinfo.text = data.projectTitle
        Textview_round_goal_roundinfo.text = data.roundGoal
        Textview_time_roundinfo.text = roundTime.toString()

        //뷰홀더를 구성하는 xml에 작성된 사용자 프로필 리사이클러뷰에 유저 이미지 어댑터를 적용
        itemView.round_user_image_list.recyclerview_user_profile.adapter = projectUserImageAdapter

        //데이터에서 사용자 이미지 문자열만으로 이루저인 리스트를 생성
        val participantList = List(data.round_participant.size) {index ->
            data.round_participant[index].user_img
        }

        //유저 이미지 어댑터에 사용자 이미지들의 리스트를 추가해줌
        projectUserImageAdapter.addAll(participantList)

        listener?.let {
            itemView.setOnClickListener {
                listener.onRoundClick(data.projectIdx, data.roundIdx, data.roundNo)
            }
        }
    }
}



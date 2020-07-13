package com.stormers.storm.project.adapter

import android.view.ViewGroup
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.project.viewholder.ParticipatedProjectViewHolder
import com.stormers.storm.ui.ParticipatedProjectListActivity

/**
 * 이 어댑터는 두 가지 액티비티에서 재사용되고 있는데, 두 액티비티에서 뷰 홀더의 크기가 조금 달라
 * 그래서 그 구분을 짓기 위해 isMain 이라는 파라미터를 만들어서 받도록 했어
 * main이면 true, ParticipatedProjectListActivity면 false를 넘겨주면 되겠지?
 * 아래 주석도 읽고 ParticipatedProjectViewHolder.kt 파일을 열어봐
 */
class ParticipatedProjectListAdapter(private val isMain: Boolean, private val listener: OnProjectClickListener) : BaseAdapter<ParticipatedProjectModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipatedProjectViewHolder {
        return ParticipatedProjectViewHolder(parent, isMain, listener)
    }

    /**
     * OnProjectClickListener도 마찬가지로 두 가지 액티비티에서 사용되기 때문에 그냥 어댑터 안에 한 번만 정의하도록 했어
     * 원래 희원이가 하던 방법은 하나의 액티비티에서만 사용되기 때문에 액티비티에 인터페이스를 정의하도록 했던 거야 !
     * 우리는 두 가지 액티비티에서 쓰니 여기에 두고 가져다 쓰자는 의도.
     */
    interface OnProjectClickListener {
        fun onProjectClick(projectIdx: Int)
    }
}
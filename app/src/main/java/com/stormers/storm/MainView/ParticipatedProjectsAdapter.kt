package com.stormers.storm.MainView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder

class ParticipatedProjectsAdapter () : BaseAdapter<ParticipatedProjectsData>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ParticipatedProjectsData> {
        return ParticipatedProjectsViewHolder(parent)
    }
}
package com.stormers.storm.MainView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseAdapter

class ParticipatedProjectsAdapter () : BaseAdapter<ParticipatedProjectsData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipatedProjectsViewHolder {
        return ParticipatedProjectsViewHolder(parent)
    }

}
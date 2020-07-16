package com.stormers.storm.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.SharedPreference

abstract class BaseViewHolder<T>(@LayoutRes layoutRes: Int, parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)) {

    protected val preference: SharedPreference by lazy { GlobalApplication.prefs }

    abstract fun bind(data: T)
}
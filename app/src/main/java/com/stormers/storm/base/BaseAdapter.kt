package com.stormers.storm.base

import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.SharedPreference

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var items: MutableList<T> = mutableListOf()

    protected val preference: SharedPreference by lazy { GlobalApplication.prefs }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setList(data: List<T>) {
        clear()
        addAll(data)
    }


    fun add(data: T) {
        insert(data, items.size)
    }


    fun addAll(data: List<T>?) {
        if (data == null) {
            return
        }

        val startIndex = items.size
        items.addAll(startIndex, data)
        notifyItemRangeInserted(startIndex, data.size)
    }

    fun getItem(position: Int): T {
        return items[position]
    }


    fun insert(data: T, position: Int) {
        items.add(position, data)
        notifyItemInserted(position)
    }


    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }


    fun change(position: Int, data: T) {
        items[position] = data
        notifyItemChanged(position)
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}
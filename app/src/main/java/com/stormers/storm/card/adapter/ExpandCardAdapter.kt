package com.stormers.storm.card.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.stormers.storm.R
import com.stormers.storm.base.BaseAdapter
import com.stormers.storm.base.BaseViewHolder
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.viewholder.CardDetailViewHolder
import kotlinx.android.synthetic.main.fragment_round_meeting_expand.view.*

class ExpandCardAdapter(private val list : ArrayList<CardModel>):PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.fragment_round_meeting_expand, container, false)


        view.imageview_scraped_card.setImageResource(list[position].getImageID(container.context))

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ==`object`
    }

    override fun getCount(): Int {
        return list.size
    }
}
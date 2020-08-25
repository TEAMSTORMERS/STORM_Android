package com.stormers.storm.card.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.card.adapter.CacheCardListAdapter
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : BaseFragment(R.layout.fragment_add_card) {

    private lateinit var addedCardListAdapter: CacheCardListAdapter

    private var mActivity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity?.stormtoolbar_roundprogress?.clearButton()

        addedCardListAdapter = CacheCardListAdapter()
        recyclerview_addcard_card.run {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(MarginDecoration(context!!, 2, 20, 20))
            adapter = addedCardListAdapter
        }

        val cards = (activity as RoundProgressActivity).cardList

        //데이터가 있으면 리사이클러뷰를 보이고 없으면 보이지 않기. 따로 Fragment 만들지 않기
        if (cards.isNotEmpty()) {
            addedCardListAdapter.setList(cards)
            recyclerview_addcard_card.visibility = View.VISIBLE
            constraintlayout_addcard_nocard.visibility = View.GONE
        } else {
            recyclerview_addcard_card.visibility = View.GONE
            constraintlayout_addcard_nocard.visibility = View.VISIBLE
        }

        cardview_addcard_add.setOnClickListener {
            goToFragment(CanvasDrawingFragment::class.java, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity = null
    }
}
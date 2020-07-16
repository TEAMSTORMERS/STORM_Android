package com.stormers.storm.RoundSetting

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : BaseFragment(R.layout.fragment_add_card) {

    private var projectIdx = -1

    private var roundIdx = -1

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }

    private lateinit var addedSavedCardAdapter: SavedCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectIdx = (activity as RoundProgressActivity).projectIdx
        roundIdx = (activity as RoundProgressActivity).roundIdx

        addedSavedCardAdapter = SavedCardAdapter(false, null)
        recyclerview_addcard_card.layoutManager = GridLayoutManager(context, 2)
        recyclerview_addcard_card.addItemDecoration(MarginDecoration(context!!, 2, 20, 20))
        recyclerview_addcard_card.adapter = addedSavedCardAdapter

        val savedCard = savedCardRepository.getAll(projectIdx, roundIdx)

        //데이터가 있으면 리사이클러뷰를 보이고 없으면 보이지 않기. 따로 Fragment 만들지 않기
        if (savedCard != null && savedCard.isNotEmpty()) {
            addedSavedCardAdapter.addAll(savedCard)
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
}
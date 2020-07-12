package com.stormers.storm.RoundSetting

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.user.UserModel
import com.stormers.storm.card.adapter.CardBitmapAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : BaseFragment(R.layout.fragment_add_card) {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }

    private lateinit var addedCardAdapter: CardBitmapAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addedCardAdapter = CardBitmapAdapter()
        recyclerview_addcard_card.layoutManager = GridLayoutManager(context, 2)
        recyclerview_addcard_card.addItemDecoration(MarginDecoration(context!!, 2, 20, 20))
        recyclerview_addcard_card.adapter = addedCardAdapter

        //우선은 projectIdx = 1, roundIdx = 1인 상황으로 가정
        val savedCard = savedCardRepository.getAllAsBitmap(1, 1)

        //데이터가 있으면 리사이클러뷰를 보이고 없으면 보이지 않기. 따로 Fragment 만들지 않기
        if (savedCard != null && savedCard.isNotEmpty()) {
            addedCardAdapter.addAll(savedCard)
            recyclerview_addcard_card.visibility = View.VISIBLE
            ImageView_addcard_nocard.visibility = View.GONE

        } else {
            recyclerview_addcard_card.visibility = View.GONE
            ImageView_addcard_nocard.visibility = View.VISIBLE
        }

        imagebutton_addcard_add.setOnClickListener {
            goToFragment(CanvasDrawingFragment::class.java, null)
        }
    }
}
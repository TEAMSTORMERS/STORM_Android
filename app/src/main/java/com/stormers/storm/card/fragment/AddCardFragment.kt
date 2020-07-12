package com.stormers.storm.RoundSetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.user.UserModel
import com.stormers.storm.card.adapter.CardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment() {

    lateinit var addedCardAdapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addedCardAdapter = CardAdapter()
        recyclerview_addcard_card.layoutManager = GridLayoutManager(context, 2)
        recyclerview_addcard_card.addItemDecoration(MarginDecoration(context!!, 2, 20, 20))
        recyclerview_addcard_card.adapter = addedCardAdapter

        //데이터가 있으면 리사이클러뷰를 보이고 없으면 보이지 않기. 따로 Fragment 만들지 않기
        if (loadCardDataOfRound().isNotEmpty()) {
            addedCardAdapter.addAll(loadCardDataOfRound())
            recyclerview_addcard_card.visibility = View.VISIBLE
            ImageView_addcard_nocard.visibility = View.GONE
        } else {
            recyclerview_addcard_card.visibility = View.GONE
            ImageView_addcard_nocard.visibility = View.VISIBLE
        }
    }

    private fun loadCardDataOfRound(): MutableList<CardModel> {
        val data = mutableListOf<CardModel>()
        val gyu = UserModel(
            "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
            "성규"
        )
        val piece = UserModel(
            "https://avatars0.githubusercontent.com/u/56873136?s=460&v=4",
            "평화"
        )
        val one = UserModel(
            "https://avatars2.githubusercontent.com/u/52772787?s=460&u=4a9f12ef174f88ec143b70f4fcaaa8f1b2d87b43&v=4",
            "희원"
        )


        data.apply {
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    gyu,
                    null
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    piece,
                    null
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    one,
                    null
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    gyu,
                    null
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    piece,
                    null
                )
            )
        }

        return data
    }
}
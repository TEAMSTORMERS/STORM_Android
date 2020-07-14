package com.stormers.storm.card.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardAdapter
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.user.UserModel
import kotlinx.android.synthetic.main.fragment_roundmeeting.*
import kotlin.math.round

class RoundmeetingFragment : Fragment() {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }
    lateinit var roundmeetingAdapter: SavedCardAdapter
    private val cardAdapter: SavedCardAdapter by lazy { SavedCardAdapter(true, null) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roundmeeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundmeetingAdapter = SavedCardAdapter(true, null)
        RecyclerView_added_card_roundmeeting.adapter = roundmeetingAdapter
        val data = savedCardRepository.getAll(1, 1)
        //cardAdapter.clear()
        roundmeetingAdapter.addAll(data)
        //savedCardRepository.getAll(1, 1)
        //roundmeetingAdapter.addAll(loadCardDataOfRound())
    }

    /*private fun loadCardDataOfRound(): MutableList<CardModel> {
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
    }*/

}
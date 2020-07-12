package com.stormers.storm.card.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.card.adapter.ExpandCardAdapter
import com.stormers.storm.R
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.user.UserModel
import com.stormers.storm.util.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_round_meeting_expend.view.*

class RoundMeetingExpandFragment : Fragment() {
    private val expandCardAdapter : ExpandCardAdapter by lazy { ExpandCardAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_round_meeting_expend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.viewpager_meetingexpend_cards.run {
            adapter = expandCardAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            setPageTransformer(ZoomOutPageTransformer())
        }

        expandCardAdapter.addAll(loadCardDataOfRound())
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
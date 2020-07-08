package com.stormers.storm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.user.UserModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*

class ParticipatedProjectDetailActivity : AppCompatActivity() {

    lateinit var scrapCardAdapter: CardAdapter
    lateinit var roundListAdapterForViewPager: RoundListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        scrapCardAdapter = CardAdapter()
        rv_scrap_card_part_detail.adapter = scrapCardAdapter
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.HORIZONTAL))
        scrapCardAdapter.addAll(loadCardDataOfRound())

        roundListAdapterForViewPager =
            RoundListAdapter()
        rv_round_part_detail.adapter = roundListAdapterForViewPager
        roundListAdapterForViewPager.addAll(loadRoundCountDatas())
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
                    gyu
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    piece
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    one
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    gyu
                )
            )
            add(
                CardModel(
                    "https://avatars2.githubusercontent.com/u/67626159?s=400&u=ec57a4e02436867cedb86350cc9e4d33d694b2f4&v=4",
                    true,
                    piece
                )
            )
        }

        return data
    }

    private fun loadRoundCountDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(
                RoundDescriptionModel(null, "ROUND 1", "라운드 목표", "총 10분 소요")
            )
            add(
                RoundDescriptionModel(null, "ROUND 2", "라운드 목표", "총 11분 소요")
            )
            add(
                RoundDescriptionModel(null, "ROUND 3", "라운드 목표", "총 12분 소요")
            )
            return datas
        }
    }
}

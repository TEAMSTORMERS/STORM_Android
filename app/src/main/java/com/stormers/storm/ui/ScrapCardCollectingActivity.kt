package com.stormers.storm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.user.UserModel
import kotlinx.android.synthetic.main.activity_scrapcard_collecting.*

class ScrapCardCollectingActivity : AppCompatActivity() {

    lateinit var scrapCollectAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrapcard_collecting)

        scrapCollectAdapter = CardAdapter()
        RecyclerView_scrap_collect.adapter = scrapCollectAdapter
        scrapCollectAdapter.addAll(loadCardDataOfRound())

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
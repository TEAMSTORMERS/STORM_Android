package com.stormers.storm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardAdapter
import kotlinx.android.synthetic.main.activity_scrapcard_collecting.*

class ScrapCardCollectingActivity : AppCompatActivity() {

    lateinit var scrapCollectAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrapcard_collecting)

        scrapCollectAdapter = CardAdapter()
        RecyclerView_scrap_collect.adapter = scrapCollectAdapter
        loadScrapCollectDatas()

    }

    private fun loadScrapCollectDatas() {

        val datas = mutableListOf<ScrapCollectData>()

        datas.apply {
            add(
                ScrapCollectData(
                    //Todo: 더미데이터 (카드 상세 이미지가 들어갈 공간입니다)
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                ScrapCollectData(
                    imageView_scrap_collect = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )

        }

        scrapCollectAdapter.addAll(datas)
    }
}
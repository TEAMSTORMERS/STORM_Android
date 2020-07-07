package com.stormers.storm.scrapcollect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.roundmeeting.recyclerview.RoundmeetingAdapter
import com.stormers.storm.roundmeeting.recyclerview.RoundmeetingData
import com.stormers.storm.scrapcollect.recyclerview.ScrapCollectAdapter
import com.stormers.storm.scrapcollect.recyclerview.ScrapCollectData
import kotlinx.android.synthetic.main.activity_scrapcard_collecting.*
import kotlinx.android.synthetic.main.fragment_roundmeeting.*

class ScrapcardCollectingActivity : AppCompatActivity() {

    lateinit var scrapCollectAdapter: ScrapCollectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrapcard_collecting)

        scrapCollectAdapter = ScrapCollectAdapter()
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
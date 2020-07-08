package com.stormers.storm.partprojectdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.partprojectdetail.horizontalrecyclerview.PartProjectDetailAdapter
import com.stormers.storm.partprojectdetail.horizontalrecyclerview.PartProjectDetailData
import com.stormers.storm.partprojectdetail.verticalrecyclerview.RoundCountAdapter
import com.stormers.storm.partprojectdetail.verticalrecyclerview.RoundCountData
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import kotlinx.android.synthetic.main.item_round_part_detail.*

class ParticipatedProjectDetailActivity : AppCompatActivity() {

    lateinit var partProjectDetailAdapter: PartProjectDetailAdapter
    lateinit var roundCountAdapter: RoundCountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        partProjectDetailAdapter = PartProjectDetailAdapter()
        rv_scrap_card_part_detail.adapter = partProjectDetailAdapter
        loadPartProjectDetailDatas()

        roundCountAdapter = RoundCountAdapter()
        rv_round_part_detail.adapter = roundCountAdapter
        loadRoundCountDatas()
    }

    private fun loadPartProjectDetailDatas() {

        val datas = mutableListOf<PartProjectDetailData>()

        datas.apply {
            add(
                PartProjectDetailData(
                    //Todo: 더미데이터 (카드 상세 이미지가 들어갈 공간입니다)
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                PartProjectDetailData(
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                PartProjectDetailData(
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                PartProjectDetailData(
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                PartProjectDetailData(
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                PartProjectDetailData(
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                PartProjectDetailData(
                    imageView_scrap_card = baseContext?.getDrawable(R.drawable.storm_logo_example)
                )
            )

        }
        partProjectDetailAdapter.addAll(datas)
    }

    private fun loadRoundCountDatas() {

        val datas = mutableListOf<RoundCountData>()

        datas.apply {
            add(
                RoundCountData(
                    //Todo: 더미데이터
                    Textview_round_roundinfo = "ROUND 1",
                    Textview_round_goal_roundinfo = "라운드 목표",
                    Textview_time_roundinfo = "총 10분 소요"
                )
            )
            add(
                RoundCountData(
                    //Todo: 더미데이터
                    Textview_round_roundinfo = "ROUND 2",
                    Textview_round_goal_roundinfo = "라운드 목표",
                    Textview_time_roundinfo = "총 11분 소요"
                )
            )
            add(
                RoundCountData(
                    //Todo: 더미데이터
                    Textview_round_roundinfo = "ROUND 3",
                    Textview_round_goal_roundinfo = "라운드 목표",
                    Textview_time_roundinfo = "총 12분 소요"
                )
            )


        }
        roundCountAdapter.addAll(datas)
    }
}

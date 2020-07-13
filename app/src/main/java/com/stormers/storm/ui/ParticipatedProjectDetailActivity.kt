package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*

class ParticipatedProjectDetailActivity : BaseActivity() {

    lateinit var scrapedCardAdapter: SavedCardAdapter
    lateinit var roundListAdapterForViewPager: RoundListAdapter

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        //Todo: DefalultValue를 우선 1로 하였으나 어떤 걸로 할지 고민해보아야함
        projectIdx = Intent().getIntExtra("projectIdx", 1)

        constraint_part_project_detail.setOnClickListener {
            val intent = Intent(this, ScrapCardCollectingActivity::class.java)
            startActivity(intent)
        }

        scrapedCardAdapter = SavedCardAdapter(false, object: SavedCardAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, ScrapedCardDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                startActivity(intent)
            }
        })

        rv_scrap_card_part_detail.adapter = scrapedCardAdapter
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.HORIZONTAL))
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.VERTICAL))

        scrapedCardAdapter.addAll(savedCardRepository.getAllScarpedCard(projectIdx))

//        roundListAdapterForViewPager = RoundListAdapter(object : OnRoundClickListener {
//            override fun onRoundClick(roundIdx: Int) {
//                val intent = Intent(this@ParticipatedProjectDetailActivity, RoundListActivity::class.java)
//                intent.putExtra("roundIdx", roundIdx)
//                startActivity(intent)
//            }
//        })

        rv_round_part_detail.adapter = roundListAdapterForViewPager
        roundListAdapterForViewPager.addAll(loadRoundCountDatas())
    }

    private fun loadRoundCountDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(
                RoundDescriptionModel("ROUND 1", null, "라운드 목표", "총 10분 소요", 1)
            )
            add(
                RoundDescriptionModel("ROUND 2", null, "라운드 목표", "총 11분 소요", 2)
            )
            add(
                RoundDescriptionModel("ROUND 3", null, "라운드 목표", "총 12분 소요", 3)
            )
            return datas
        }
    }
}

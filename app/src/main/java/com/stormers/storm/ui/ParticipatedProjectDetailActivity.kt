package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.network.ProjectInterface
import com.stormers.storm.project.network.ResponseProjectData
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class ParticipatedProjectDetailActivity : BaseActivity() {

    lateinit var scrapedCardAdapter: SavedCardAdapter
    lateinit var roundListAdapterForViewPager: RoundListAdapter

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1

    private lateinit var retrofitClient: ProjectInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        //Todo: DefalultValue를 우선 1로 하였으나 어떤 걸로 할지 고민해보아야함
        projectIdx = Intent().getIntExtra("projectIdx", 1)

        retrofitClient = RetrofitClient.create(ProjectInterface::class.java)

        retrofitClient.responseProjectData(projectIdx.toString()).enqueue(object : Callback<ResponseProjectData> {
            override fun onFailure(call: Call<ResponseProjectData>, t: Throwable) {
                if (t.message != null){
                    Log.d("test", t.message!!)
                } else {
                    Log.d("test", "oops")
                }
            }

            override fun onResponse(
                call: Call<ResponseProjectData>,
                response: Response<ResponseProjectData>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("test", "받아온 프로젝트 이름 : ${response.body()!!.data?.project_name}")
                    }
                    else {
                        Log.d("Test", "실패")
                    }
                } else {
                    Log.d("test", "${response.message()} , ${response.errorBody()}")
                }
            }
        })

        constraintlayout_participatedproject_seemore.setOnClickListener {
            val intent = Intent(this, ScrapCardCollectingActivity::class.java)
            startActivity(intent)
        }

        scrapedCardAdapter = SavedCardAdapter(false, object: SavedCardAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, ScrapedCardDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)
                startActivity(intent)
            }
        })

        rv_scrap_card_part_detail.adapter = scrapedCardAdapter
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.HORIZONTAL))
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.VERTICAL))

        scrapedCardAdapter.addAll(savedCardRepository.getAllScrapedCard(projectIdx))

        roundListAdapterForViewPager = RoundListAdapter(object : RoundListAdapter.OnRoundClickListener {
            override fun onRoundClick(projectIdx: Int, roundIdx: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, RoundListActivity::class.java)
                intent.putExtra("roundIdx", roundIdx)
                startActivity(intent)
            }
        })

        constraintlayout_participatedproject_seemore.setOnClickListener {
            startActivity(Intent(this@ParticipatedProjectDetailActivity, ScrapedCardDetailActivity::class.java))
        }

        rv_round_part_detail.adapter = roundListAdapterForViewPager
        roundListAdapterForViewPager.addAll(loadRoundCountDatas())
    }

    private fun loadRoundCountDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            datas.apply {
                add(RoundDescriptionModel(null, null, "베개와 유리병의 공통점은?", "11분 소요", 0, projectIdx))
                add(RoundDescriptionModel(null, null, "Pillow 와 Glass 의 공통점은?", "11분 소요", 1, projectIdx))
                add(RoundDescriptionModel(null, null, "평화와 희원이의 공통점은?", "11분 소요", 2, projectIdx))
            }
            return datas
        }
    }
}

package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.network.ProjectInterface
import com.stormers.storm.project.network.ResponseProjectData
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.network.FinalRoundInterface
import com.stormers.storm.round.network.ResponseFinalRoundData
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParticipatedProjectDetailActivity : BaseActivity() {

    lateinit var scrapedCardAdapter: SavedCardAdapter
    lateinit var roundListAdapterForViewPager: RoundListAdapter

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1

    private lateinit var retrofitClient_roundInfo: FinalRoundInterface
    private lateinit var retrofitClient: ProjectInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        //Todo: DefalultValue를 우선 1로 하였으나 어떤 걸로 할지 고민해보아야함
        projectIdx = Intent().getIntExtra("projectIdx", 1)

        retrofitClient = RetrofitClient.create(ProjectInterface::class.java)
        retrofitClient_roundInfo = RetrofitClient.create(FinalRoundInterface::class.java)

        retrofitClient_roundInfo.responseFinalRoundData(projectIdx).enqueue(object : Callback<ResponseFinalRoundData> {
            override fun onFailure(call: Call<ResponseFinalRoundData>, t: Throwable) {
                if (t.message != null){
                    Log.d("PartProDetailRound", t.message!!)
                } else {
                    Log.d("PartProDetailRound", "통신실패")
                }
            }

            override fun onResponse(call: Call<ResponseFinalRoundData>, response: Response<ResponseFinalRoundData>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        for (i in response.body()!!.data.indices) {
                            Log.d("PartProDetailRound", "받아온 라운드 정보 : ${response.body()!!.data[i].projectTitle}")
                        }
                        roundListAdapterForViewPager.addAll(response.body()!!.data)
                    }
                    else {
                        Log.d("PartProDetailRound", "통신실패")
                    }
                } else {
                    Log.d("PartProDetailRound", "${response.message()} , ${response.errorBody()}")
                }
            }
        })

        retrofitClient.responseProjectData(projectIdx).enqueue(object : Callback<ResponseProjectData> {
            override fun onFailure(call: Call<ResponseProjectData>, t: Throwable) {
                if (t.message != null){
                    Log.d("PartProDetailActivity", t.message!!)
                } else {
                    Log.d("PartProDetailActivity", "통신실패")
                }
            }

            override fun onResponse(call: Call<ResponseProjectData>, response: Response<ResponseProjectData>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("PartProDetailActivity", "받아온 프로젝트 이름 : ${response.body()!!.data.project_name}")

                        textview_projectcard_title.text = response.body()!!.data.project_name
                        textView_date_part_detail.text = response.body()!!.data.project_date

                        val roundCount = StringBuilder()
                        roundCount.append("ROUND 총 ")
                            .append(response.body()!!.data.round_count.toString())
                            .append("회")

                        textView_round_count_part_detail.text = roundCount
                    }
                    else {
                        Log.d("PartProDetailActivity", "통신실패 ${response.message()}")
                    }
                } else {
                    Log.d("PartProDetailActivity", "${response.message()} , ${response.errorBody()}")
                }
            }
        })

        constraintlayout_participatedproject_seemore.setOnClickListener {
            val intent = Intent(this, ScrapCardCollectingActivity::class.java)
            intent.putExtra("projectIdx", projectIdx)
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


        val data = savedCardRepository.getAllScrapedCard(projectIdx)

        if (data.isNotEmpty()) {
            scrapedCardAdapter.addAll(savedCardRepository.getAllScrapedCard(projectIdx))
            textview_noscraped.visibility = View.GONE

        } else {
            textview_noscraped.visibility = View.VISIBLE
        }

        roundListAdapterForViewPager = RoundListAdapter(object : RoundListAdapter.OnRoundClickListener {
            override fun onRoundClick(projectIdx: Int, roundIdx: Int, roundNo: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, RoundListActivity::class.java)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("projectIdx", this@ParticipatedProjectDetailActivity.projectIdx)
                intent.putExtra("roundNo", roundNo)
                startActivity(intent)
            }
        })

        rv_round_part_detail.adapter = roundListAdapterForViewPager
    }
}

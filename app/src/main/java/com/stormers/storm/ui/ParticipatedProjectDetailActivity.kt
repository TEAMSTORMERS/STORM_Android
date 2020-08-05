package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.adapter.ProjectUserImageAdapter
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.project.network.response.ResponseProjectFinalInfoModel
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseFinalRoundData
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import kotlinx.android.synthetic.main.layout_list_user_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParticipatedProjectDetailActivity : BaseActivity() {

    lateinit var scrapedCardAdapter: SavedCardAdapter
    lateinit var roundListAdapterForViewPager: RoundListAdapter

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1

    private lateinit var retrofitClient_roundInfo: RequestRound
    private lateinit var retrofitClient: RequestProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        projectIdx = intent.getIntExtra("projectIdx", -1)

        retrofitClient = RetrofitClient.create(RequestProject::class.java)
        retrofitClient_roundInfo = RetrofitClient.create(RequestRound::class.java)

        //fixme : 어댑터 적용 
        val projectUserImageAdapter = ProjectUserImageAdapter()
        recyclerview_user_profile.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL,false)
        recyclerview_user_profile.addItemDecoration(MarginDecoration(baseContext, 9, RecyclerView.HORIZONTAL))
        recyclerview_user_profile.adapter = projectUserImageAdapter

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

        retrofitClient.responseProjectData(projectIdx).enqueue(object : Callback<ResponseProjectFinalInfoModel> {
            override fun onFailure(call: Call<ResponseProjectFinalInfoModel>, t: Throwable) {
                if (t.message != null){
                    Log.d("PartProDetailActivity", t.message!!)
                } else {
                    Log.d("PartProDetailActivity", "통신실패")
                }
            }

            override fun onResponse(call: Call<ResponseProjectFinalInfoModel>, response: Response<ResponseProjectFinalInfoModel>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("PartProDetailActivity", "받아온 프로젝트 이름 : ${response.body()!!.data.projectName}")

                        textview_projectcard_title.text = response.body()!!.data.projectName
                        textView_date_part_detail.text = response.body()!!.data.projectDate

                        projectUserImageAdapter.addAll(response.body()!!.data.projectParticipantsList)

                        val roundCount = StringBuilder()
                        roundCount.append("ROUND 총 ")
                            .append(response.body()!!.data.roundCount.toString())
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

    override fun onResume() {
        super.onResume()

        val data = savedCardRepository.getAllScrapedCard(projectIdx)

        if (data.isNotEmpty()) {
            scrapedCardAdapter.clear()
            scrapedCardAdapter.addAll(savedCardRepository.getAllScrapedCard(projectIdx))
            textview_noscraped.visibility = View.GONE

        } else {
            textview_noscraped.visibility = View.VISIBLE
        }
    }

   /*
   fun roundUserImageList() {
        val roundUserImageAdapter = RoundUserImageAdapter()
        rv_round_part_detail.adapter = roundUserImageAdapter
        rv_round_part_detail.layoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.HORIZONTAL, false)

    }
    */

}

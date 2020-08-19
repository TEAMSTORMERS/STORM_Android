package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.project.ProjectRepository
import com.stormers.storm.project.adapter.ProjectParticipantsAdapter
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import kotlinx.android.synthetic.main.layout_list_user_profile.*

class ParticipatedProjectDetailActivity : BaseActivity() {

    companion object {
        private const val TAG = "ProjectsDetailActivity"
    }

    lateinit var scrapedCardListAdapter: CardListAdapter
    lateinit var roundListAdapter: RoundListAdapter

    private val cardRepository: CardRepository by lazy { CardRepository() }

    private var projectIdx = -1

    private var isAfterProject = false

    private lateinit var retrofitClient_roundInfo: RequestRound
    private lateinit var retrofitClient: RequestProject

    private val projectRepository: ProjectRepository by lazy { ProjectRepository.getInstance() }

    private lateinit var projectParticipantsAdapter: ProjectParticipantsAdapter

    private var currentProject: ProjectModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_detail)

        //필요한 세팅값 불러오기
        projectIdx = intent.getIntExtra("projectIdx", -1)
        isAfterProject = intent.getBooleanExtra("isAfterProject", false)

        //상황에 따른 툴바 반경
        stormtoolbar_participateddetail.run {
            if (!isAfterProject) {
                setBackButton()
                setMyPageButton()
            } else {
                setExitButton(View.OnClickListener {
                    finish()
                })
            }
        }

        //
        projectParticipantsAdapter = ProjectParticipantsAdapter()
        recyclerview_user_profile.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL,false)
        recyclerview_user_profile.addItemDecoration(MarginDecoration(baseContext, 9, RecyclerView.HORIZONTAL))
        recyclerview_user_profile.adapter = projectParticipantsAdapter

        roundListAdapter = RoundListAdapter(object : RoundListAdapter.OnRoundClickListener {
            override fun onRoundClick(roundIdx: Int, roundNo: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, RoundListActivity::class.java)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("projectIdx", this@ParticipatedProjectDetailActivity.projectIdx)
                intent.putExtra("roundNo", roundNo)
                startActivity(intent)
            }
        })

        scrapedCardListAdapter = CardListAdapter(true, object: CardListAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, ScrapedCardDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)
                startActivity(intent)
            }
        })

        projectRepository.get(projectIdx, object : ProjectRepository.GetProjectCallback {
            override fun onProjectLoaded(project: ProjectModel) {
                currentProject = project
                projectParticipantsAdapter.setList(currentProject!!.projectParticipants!!)
                roundListAdapter.setList(currentProject!!.projectRounds!!)
                initProjectInfo(currentProject!!)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "Load Error: $projectIdx")
            }
        })

        cardRepository.getScrapAllForList(projectIdx, object: CardRepository.LoadEnumCardsCallback {
            override fun onCardLoaded(cards: List<CardEnumModel>) {
                scrapedCardListAdapter.setList(cards)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "Load Error: $projectIdx")
            }
        })


        constraintlayout_participatedproject_seemore.setOnClickListener {
            val intent = Intent(this, ScrapCardCollectingActivity::class.java)
            intent.putExtra("projectIdx", projectIdx)
            startActivity(intent)
        }

        rv_scrap_card_part_detail.adapter = scrapedCardListAdapter
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.HORIZONTAL))
        rv_scrap_card_part_detail.addItemDecoration(MarginDecoration(this, 15, RecyclerView.VERTICAL))

        rv_round_part_detail.adapter = roundListAdapter

    }

    private fun initProjectInfo(project: ProjectModel) {
        project.let {
            textview_projectcard_title.text = it.projectName
            //Todo: ProjectDate 추가하기
            textView_date_part_detail.text = "2020/08/18"
            projectParticipantsAdapter.setList(it.projectParticipants!!)

            val numberOfParticipants = it.projectParticipants.size

            if( numberOfParticipants > 5 ){
                textview_extra_participants_info.text = StringBuilder("+").append(numberOfParticipants - 5)
                textview_extra_participants_info.visibility = View.VISIBLE
            }

            textView_round_count_part_detail.text = StringBuilder("ROUND 총 ")
                .append(it.projectRounds!!.size).append("회").toString()
        }
    }

    override fun onResume() {
        super.onResume()

        cardRepository.getScrapAllForList(projectIdx, object: CardRepository.LoadEnumCardsCallback {
            override fun onCardLoaded(cards: List<CardEnumModel>) {
                scrapedCardListAdapter.setList(cards)
                textview_noscraped.visibility = View.GONE
            }

            override fun onDataNotAvailable() {
                textview_noscraped.visibility = View.VISIBLE
            }
        })
    }

}

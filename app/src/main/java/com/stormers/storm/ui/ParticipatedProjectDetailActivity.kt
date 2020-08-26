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
import com.stormers.storm.card.adapter.ScrapedCardListAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.project.data.source.ProjectRepository
import com.stormers.storm.project.adapter.ProjectParticipantsAdapter
import com.stormers.storm.project.data.source.ProjectsDataSource
import com.stormers.storm.project.data.source.local.ProjectsLocalDataSource
import com.stormers.storm.project.data.source.remote.ProjectsRemoteDataSource
import com.stormers.storm.project.model.ProjectDetailInfo
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.data.source.RoundDataSource
import com.stormers.storm.round.data.source.RoundRepository
import com.stormers.storm.round.data.source.local.RoundsLocalDataSource
import com.stormers.storm.round.data.source.remote.RoundsRemoteDataSource
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import kotlinx.android.synthetic.main.layout_list_user_profile.*

class ParticipatedProjectDetailActivity : BaseActivity() {

    companion object {
        private const val TAG = "ProjectsDetailActivity"
    }

    lateinit var scrapedCardListAdapter: ScrapedCardListAdapter
    lateinit var roundListAdapter: RoundListAdapter

    private var projectIdx = -1

    private var isAfterProject = false

    private val projectRepository: ProjectRepository by lazy {
        ProjectRepository.getInstance(ProjectsRemoteDataSource, ProjectsLocalDataSource.getInstance()) }

    private val cardRepository : CardRepository by lazy {
        CardRepository.getInstance(CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private val roundRepository : RoundRepository by lazy {
        RoundRepository.getInstance(RoundsRemoteDataSource, RoundsLocalDataSource.getInstance()) }

    private lateinit var projectParticipantsAdapter: ProjectParticipantsAdapter

    private var projectName: String? = null

    private val userIdx = GlobalApplication.userIdx

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

        //스크랩한 카드 더 보기 버튼 초기화
        constraintlayout_participatedproject_seemore.setOnClickListener {
            val intent = Intent(this, ScrapCardCollectingActivity::class.java)
            intent.putExtra("projectIdx", projectIdx)
            intent.putExtra("projectName", projectName)
            startActivity(intent)
        }

        //참가자 리사이클러뷰 어댑터 초기화
        projectParticipantsAdapter = ProjectParticipantsAdapter()

        //참가자 리사이클러뷰 초기화
        recyclerview_user_profile.run {
            layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(MarginDecoration(baseContext, 9, RecyclerView.HORIZONTAL))
            adapter = projectParticipantsAdapter
        }

        //스크랩한 카드 리사이클러뷰 어댑터 초기화
        scrapedCardListAdapter = ScrapedCardListAdapter(false, object: ScrapedCardListAdapter.OnCardClickListener {
            override fun onCardClick(cardIdx: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, ScrapedRoundCardExpandActivity::class.java)
                intent.putExtra("cardIdx", cardIdx)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("projectName", projectName)
                startActivity(intent)
            }
        })

        //스크랩한 카드 리사이클러뷰 초기화
        recyclerview_participateddetail_scrapedcard.run {
            adapter = scrapedCardListAdapter
            addItemDecoration(MarginDecoration(this@ParticipatedProjectDetailActivity, 15, RecyclerView.HORIZONTAL))
            addItemDecoration(MarginDecoration(this@ParticipatedProjectDetailActivity, 15, RecyclerView.VERTICAL))
        }

        //라운드 리사이클러뷰 어댑터 초기화
        roundListAdapter = RoundListAdapter(object : RoundListAdapter.OnRoundClickListener {
            override fun onRoundClick(roundIdx: Int, roundNo: Int) {
                val intent = Intent(this@ParticipatedProjectDetailActivity, RoundListActivity::class.java)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("projectIdx", this@ParticipatedProjectDetailActivity.projectIdx)
                intent.putExtra("roundNo", roundNo)
                intent.putExtra("projectName", projectName)
                startActivity(intent)
            }
        })

        //라운드 리사이클러뷰 초기화
        recyclerview_participateddetail_roundlist.adapter = roundListAdapter

        //해당 프로젝트에 대한 정보 가져오기
        projectRepository.getProjectDetailInfo(projectIdx, object : ProjectsDataSource.GetProjectCallback<ProjectDetailInfo> {
            override fun onProjectLoaded(project: ProjectDetailInfo) {
                initProjectInfo(project)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "Load Error: $projectIdx")
            }
        })

        roundRepository.getRoundsInfo(projectIdx, userIdx, object: RoundDataSource.LoadRoundsCallback<RoundDescriptionModel> {
            override fun onRoundsLoaded(rounds: List<RoundDescriptionModel>) {
                roundListAdapter.setList(rounds)
            }

            override fun onDataNotAvailable() {
                Log.d(TAG, "getRoundInfo: No round data")
            }
        })
    }

    override fun onResume() {
        super.onResume()

        //스크랩한 카드는 변동이 있을 수 있으니 onResume()에서 목록을 초기화
        cardRepository.getScrapedCardsWithInfo(projectIdx, userIdx, object: CardDataSource.GetCardCallback<ScrapedCardModel> {
            override fun onCardLoaded(card: ScrapedCardModel) {
                scrapedCardListAdapter.setList(card.cardItem)
                recyclerview_participateddetail_scrapedcard.visibility = View.VISIBLE
                constraintlayout_participatedproject_seemore.visibility = View.VISIBLE
                textview_participateddetail_noscraped.visibility = View.GONE
            }

            override fun onDataNotAvailable() {
                recyclerview_participateddetail_scrapedcard.visibility = View.GONE
                constraintlayout_participatedproject_seemore.visibility = View.GONE
                textview_participateddetail_noscraped.visibility = View.VISIBLE
            }
        })
    }

    private fun initProjectInfo(project: ProjectDetailInfo) {
        project.run {
            this@ParticipatedProjectDetailActivity.projectName = projectName
            textview_projectcard_title.text = projectName

            textview_participateddetail_date.text = projectDate
            projectParticipantsAdapter.setList(projectParticipantsList)

            val numberOfParticipants = projectParticipantsList.size

            if( numberOfParticipants > 5 ){
                textview_extra_participants_info.text = StringBuilder("+").append(numberOfParticipants - 5)
                textview_extra_participants_info.visibility = View.VISIBLE
            }

            textView_round_count_part_detail.text = StringBuilder("ROUND 총 ")
                .append(roundCount).append("회").toString()
        }
    }
}

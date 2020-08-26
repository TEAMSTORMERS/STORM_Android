package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.round.data.source.RoundRepository
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.data.source.RoundDataSource
import com.stormers.storm.round.data.source.local.RoundsLocalDataSource
import com.stormers.storm.round.data.source.remote.RoundsRemoteDataSource
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*

class RoundListActivity : BaseActivity() {

    companion object {
        private const val TAG = "RoundListActivity"

        private const val REQUEST_EXPAND = 100

        const val RESULT_DIRTY = 10
    }

    lateinit var roundListAdapterForViewPager: RoundListAdapter

    private lateinit var cardListAdapter: CardListAdapter

    private var roundIdx = -1

    private var projectIdx = -1

    private var roundNo = -1

    private var roundPurpose = ""

    private var roundTime = -1

    private var projectName: String? = null

    private val userIdx = GlobalApplication.userIdx

    private val cardRepository : CardRepository by lazy {
        CardRepository.getInstance(CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private val roundRepository : RoundRepository by lazy {
        RoundRepository.getInstance(RoundsRemoteDataSource, RoundsLocalDataSource.getInstance()) }

    private val cacheRounds = HashMap<Int, RoundInfoWithCardsModel>()

    private val cacheDirty = HashMap<Int, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)

        //툴바 초기화
        stormtoolbar_roundlist_toolbar.setBackButton()

        //해당 화면에 필요한 정보 뽑아내기
        projectIdx = intent.getIntExtra("projectIdx", -1)
        roundIdx = intent.getIntExtra("roundIdx", -1)
        roundNo = intent.getIntExtra("roundNo", -1)
        projectName = intent.getStringExtra("projectName")

        //넘겨받은 값이 없으면 잘못된 접근
        if (projectIdx == -1 || roundIdx == -1 || roundNo == -1) {
            Log.e(TAG, "Wrong access. projectIdx: $projectIdx, roundIdx: $roundIdx, roundNo: $roundNo")
            return
        }

        //카드 리사이클러뷰 어댑터 초기화
        cardListAdapter = CardListAdapter(object : CardListAdapter.OnCardClickListener {

            override fun onCardClick(cardIdx: Int) {
                val intent = Intent(this@RoundListActivity, RoundCardExpandActivity::class.java)
                intent.putExtra("cardIdx", cardIdx)
                intent.putExtra("projectName", projectName)
                intent.putExtra("roundNumber", roundNo)
                intent.putExtra("roundPurpose", roundPurpose)
                intent.putExtra("roundTime", roundTime)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("projectIdx", projectIdx)

                startActivityForResult(intent, REQUEST_EXPAND)
            }
        })

        //카드 리사이클러뷰 초기화
        recyclerview_roundlist_cardlist.run {
            adapter = cardListAdapter
            addItemDecoration(MarginDecoration(this@RoundListActivity, 2, 20, 20))
        }

        //라운드 뷰페이저 어댑터 초기화
        roundListAdapterForViewPager = RoundListAdapter(null)
        roundListAdapterForViewPager.projectName = projectName

        //라운드 정보 DB에서 불러오기
        roundRepository.getRoundsInfo(projectIdx, userIdx,  object : RoundDataSource.LoadRoundsCallback<RoundDescriptionModel> {
            override fun onRoundsLoaded(rounds: List<RoundDescriptionModel>) {
                roundListAdapterForViewPager.addAll(rounds)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "No round in the project ($projectIdx)")
            }
        })

        //라운드 뷰페이저 초기화
        viewpager_roundcardlist_round.run {
            adapter = roundListAdapterForViewPager
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            currentItem = roundNo - 1
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //선택된 라운드의 카드 리스트를 보여줌
                    getRoundAndCardsInfo(roundListAdapterForViewPager.getItem(position).roundIdx)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_EXPAND && resultCode == RESULT_DIRTY) {
            val roundIdx = data?.getIntExtra("roundIdx", -1)

            if (roundIdx == null || roundIdx == -1) {
                return
            }

            cacheDirty[roundIdx] = true
            getRoundAndCardsInfo(roundIdx)
        }
    }

    private fun getRoundAndCardsInfo(roundIdx: Int) {
        if (cacheRounds.containsKey(roundIdx) && !cacheDirty[roundIdx]!!) {
            setRoundAndCardsInfo(cacheRounds[roundIdx]!!)
        } else {
            cardRepository.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, userIdx,
                object : CardDataSource.GetCardCallback<RoundInfoWithCardsModel> {

                    override fun onCardLoaded(card: RoundInfoWithCardsModel) {
                        setRoundAndCardsInfo(card)
                        cacheRounds[roundIdx] = card
                        cacheDirty[roundIdx] = false
                    }

                    override fun onDataNotAvailable() {
                        Log.e(TAG, "No data in DB. projectIdx: $projectIdx, roundIdx: $roundIdx")
                    }
                })
        }
    }

    private fun setRoundAndCardsInfo(info: RoundInfoWithCardsModel) {
        roundTime = info.roundTime
        roundPurpose = info.roundPurpose

        cardListAdapter.setList(info.cardWithOwnerList)
    }
}
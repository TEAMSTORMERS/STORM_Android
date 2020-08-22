package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.round.RoundRepository
import com.stormers.storm.round.adapter.RoundListAdapter
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*

class RoundListActivity : BaseActivity() {

    companion object {
        private const val TAG = "RoundListActivity"
    }

    lateinit var roundListAdapterForViewPager: RoundListAdapter

    private lateinit var cardListAdapter: CardListAdapter

    private var roundIdx = -1

    private var projectIdx = -1

    private var roundNo = -1

    private var projectName: String? = null

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance() }

    private val roundRepository: RoundRepository by lazy { RoundRepository.getInstance() }

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
        }

        //카드 리사이클러뷰 어댑터 초기화
        cardListAdapter = CardListAdapter(true, object : CardListAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardIdx: Int) {
                val intent = Intent(this@RoundListActivity, RoundCardExpandActivity::class.java)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardIdx", cardIdx)
                intent.putExtra("projectName", projectName)

                startActivity(intent)
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
        roundRepository.getAll(projectIdx, object : RoundRepository.LoadRoundsCallback {
            override fun onRoundsLoaded(rounds: List<RoundModel>) {
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
                    setCardList(roundListAdapterForViewPager.getItem(position).roundIdx)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()

        //스크랩한 카드가 변경될 수 있으므로 여기서 갱신
        setCardList(roundIdx)
    }

    private fun setCardList(roundIdx: Int) {
        cardRepository.getAllForList(roundIdx, object: CardRepository.LoadCardModel<CardEnumModel> {
            override fun onCardsLoaded(cards: List<CardEnumModel>) {
                cardListAdapter.setList(cards)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "No data in DB. projectIdx: $projectIdx, roundIdx: $roundIdx")
            }
        })
    }
}
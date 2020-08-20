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

        stormtoolbar_roundlist_toolbar.setBackButton()

        projectIdx = intent.getIntExtra("projectIdx", -1)
        roundIdx = intent.getIntExtra("roundIdx", -1)
        roundNo = intent.getIntExtra("roundNo", -1)
        projectName = intent.getStringExtra("projectName")

        if (projectIdx == -1 || roundIdx == -1 || roundNo == -1) {
            Log.e(TAG, "Wrong access. projectIdx: $projectIdx, roundIdx: $roundIdx, roundNo: $roundNo")
        }

        roundListAdapterForViewPager = RoundListAdapter(null)
        roundListAdapterForViewPager.projectName = projectName

        cardListAdapter = CardListAdapter(true, object : CardListAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(this@RoundListActivity, ScrapedCardDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)

                startActivity(intent)
            }
        })

        roundRepository.getAll(projectIdx, object : RoundRepository.LoadRoundsCallback {
            override fun onRoundsLoaded(rounds: List<RoundModel>) {
                roundListAdapterForViewPager.addAll(rounds)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "No round in the project ($projectIdx)")
            }
        })


        recyclerView_roundcardlist_cardlist.run {
            adapter = cardListAdapter
            addItemDecoration(MarginDecoration(this@RoundListActivity, 2, 20, 20))
        }

        viewpager_roundcardlist_round.run {
            adapter = roundListAdapterForViewPager
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            currentItem = roundNo - 1
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val roundIdx = roundListAdapterForViewPager.getItem(position).roundIdx

                    setCardList(roundIdx)
                }
            })
        }
    }

    private fun setCardList(roundIdx: Int) {
        cardRepository.getAllForList(projectIdx, roundIdx, object: CardRepository.LoadEnumCardsCallback {
            override fun onCardLoaded(cards: List<CardEnumModel>) {
                cardListAdapter.setList(cards)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "No data in DB. projectIdx: $projectIdx, roundIdx: $roundIdx")
            }
        })
    }


    override fun onResume() {
        super.onResume()

        setCardList(roundIdx)
    }
}
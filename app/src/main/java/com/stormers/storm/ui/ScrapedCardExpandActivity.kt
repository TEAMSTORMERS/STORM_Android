package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ExpandCardFragment
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.round.RoundRepository
import com.stormers.storm.round.model.RoundModel
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class ScrapedCardExpandActivity : BaseActivity(), ExpandCardFragment.OnCardPageChangeCallback {

    companion object {
        private const val TAG = "ScrapedCardExpandActivity"
    }

    private val cacheRoundData = HashMap<Int, RoundModel>()

    private val cardRepository: CardRepository by lazy { CardRepository.getInstance() }

    private val roundRepository: RoundRepository by lazy { RoundRepository.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        val selectedCardIdx = intent.getIntExtra("cardIdx", -1)
        val selectedProjectIdx = intent.getIntExtra("projectIdx", -1)
        val selectedProjectName = intent.getStringExtra("projectName")

        initView(selectedProjectName)

        goToFragment(ExpandCardFragment::class.java, Bundle().apply {
            if (selectedCardIdx != -1) {
                putInt("cardIdx", selectedCardIdx)
            }
            if (selectedProjectIdx != -1) {
                putInt("projectIdx", selectedProjectIdx)
            }
            putBoolean("scrapList", true)
        })
    }

    override fun onCardPageChanged(position: Int, totalCount: Int, cardModel: CardModel) {
        cardRepository.get(cardModel.cardIdx, object : CardRepository.GetCardModel<CardEntity> {
            override fun onCardLoaded(card: CardEntity) {
                getRoundData(card.roundIdx)
            }

            @SuppressLint("LongLogTag")
            override fun onDataNotAvailable() {
                Log.e(TAG, "onCardPageChanged: No card data. cardIdx (${cardModel.cardIdx}")
            }
        })

    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_expandcard_fragment
    }

    private fun initView(projectName: String?) {
        textview_expandcard_count.visibility = View.GONE

        textview_expandcard_projectname.text = projectName
    }

    private fun getRoundData(roundIdx: Int) {
        if (cacheRoundData.containsKey(roundIdx)) {
            setRoundData(cacheRoundData[roundIdx]!!)
            return
        }

        roundRepository.get(roundIdx, object : RoundRepository.GetRoundCallback {
            override fun onRoundLoaded(round: RoundModel) {
                cacheRoundData[roundIdx] = round
                setRoundData(round)
            }

            @SuppressLint("LongLogTag")
            override fun onDataNotAvailable() {
                Log.e(TAG, "getRoundData: No round data. roundIdx ($roundIdx)")
            }
        })
    }

    private fun setRoundData(roundModel: RoundModel) {
        textview_expandcard_roundnumber.text = StringBuilder("Round ")
            .append(roundModel.roundNumber).toString()

        textview_expandcard_roundpurpose.text = roundModel.roundPurpose

        textview_expandcard_roundtime.text = StringBuilder("총 ")
            .append(roundModel.roundTime).append("분 소요").toString()
    }
}
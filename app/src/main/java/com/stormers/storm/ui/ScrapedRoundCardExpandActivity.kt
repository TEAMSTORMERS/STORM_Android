package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ExpandScrapedCardFragment
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class ScrapedRoundCardExpandActivity : BaseActivity(), ExpandScrapedCardFragment.OnScrapedCardPageChangeCallback {

    companion object {
        private const val TAG = "ScrapedCardExpandActivity"
    }

    private val cacheRoundData = HashMap<Int, RoundModelOfScrapedCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        scrollViewKeyBoard(scrollview_expandcard)

        stormtoolbar_expandcard.setBackButton()

        val selectedCardIdx = intent.getIntExtra("cardIdx", -1)
        val selectedProjectIdx = intent.getIntExtra("projectIdx", -1)
        val selectedProjectName = intent.getStringExtra("projectName")

        initProjectInfo(selectedProjectName)

        goToFragment(ExpandScrapedCardFragment::class.java, Bundle().apply {
            if (selectedCardIdx != -1) {
                putInt("cardIdx", selectedCardIdx)
            }
            if (selectedProjectIdx != -1) {
                putInt("projectIdx", selectedProjectIdx)
            }
        })
    }

    override fun onCardPageChanged(position: Int, totalCount: Int, card: ScrapedCardWithRoundInfo) {
        initRoundInfo(RoundModelOfScrapedCard(card.roundNumber, card.roundPurpose, card.roundTime))
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_expandcard_fragment
    }

    private fun initProjectInfo(projectName: String?) {
        textview_expandcard_count.visibility = View.GONE

        textview_expandcard_projectname.text = projectName
    }

    private fun initRoundInfo(round: RoundModelOfScrapedCard) {
        round.run {
            if (cacheRoundData.containsKey(roundNumber)) {
                setRoundData(cacheRoundData[roundNumber]!!)
            } else {
                setRoundData(this)
                cacheRoundData[roundNumber] = this
            }
        }
    }

    private fun setRoundData(round: RoundModelOfScrapedCard) {
        round.run {
            textview_expandcard_roundnumber.text = StringBuilder("Round ")
                .append(roundNumber).toString()

            textview_expandcard_roundpurpose.text = roundPurpose

            textview_expandcard_roundtime.text = StringBuilder("총 ")
                .append(roundTime).append("분 소요").toString()
        }
    }

    data class RoundModelOfScrapedCard(
        val roundNumber: Int,
        val roundPurpose: String,
        val roundTime: Int
    )
}
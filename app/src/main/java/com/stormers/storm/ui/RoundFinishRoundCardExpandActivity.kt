package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ExpandRoundCardFragment
import com.stormers.storm.card.model.CardWithOwnerModel
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class RoundFinishRoundCardExpandActivity : BaseActivity(), ExpandRoundCardFragment.OnRoundCardPageChangeCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        scrollViewKeyBoard(scrollview_expandcard)

        val selectedCardIdx = intent.getIntExtra("cardIdx", -1)
        val currentProjectInx = GlobalApplication.currentProject!!.projectIdx
        val currentRoundIdx = GlobalApplication.currentRound!!.roundIdx

        constraintlayout_expandcard_roundinfo.visibility = View.INVISIBLE

        goToFragment(ExpandRoundCardFragment::class.java, Bundle().apply {
            if (selectedCardIdx != -1) {
                putInt("cardIdx", selectedCardIdx)
            }
            putInt("roundIdx", currentRoundIdx)
            putInt("projectIdx", currentProjectInx)
        })
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_expandcard_fragment
    }

    override fun onCardPageChanged(position: Int, totalCount: Int, card: CardWithOwnerModel) {
        setCount(totalCount, position)
    }

    private fun setCount(totalCount: Int, currentCount: Int) {
        textview_expandcard_count.text = getCountText(totalCount, currentCount)
    }

    private fun getCountText(totalCount: Int, currentCount: Int): String {
        val cardCount = StringBuilder()
        cardCount.append("(")
            .append((currentCount+1).toString())
            .append("/")
            .append(totalCount.toString())
            .append(")")

        return cardCount.toString()
    }
}
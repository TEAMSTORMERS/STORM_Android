package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ExpandCardFragment
import com.stormers.storm.card.model.CardModel
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class RoundFinishCardExpandActivity : BaseActivity(), ExpandCardFragment.OnCardPageChangeCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        val selectedCardIdx = intent.getIntExtra("cardIdx", -1)

        constraintlayout_expandcard_roundinfo.visibility = View.GONE

        goToFragment(ExpandCardFragment::class.java, Bundle().apply {
            if (selectedCardIdx != -1) {
                putInt("cardIdx", selectedCardIdx)
            }
            putInt("roundIdx", GlobalApplication.currentRound!!.roundIdx)
        })
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_expandcard_fragment
    }

    override fun onCardPageChanged(position: Int, totalCount: Int, cardModel: CardModel) {
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
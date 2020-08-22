package com.stormers.storm.ui

import android.os.Bundle
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R

import com.stormers.storm.round.base.BaseExpandCardActivity
import com.stormers.storm.util.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class RoundMeetingExpandActivity : BaseExpandCardActivity(false, R.layout.activity_expandcard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedCardCount(textview_expandcard_count)
        currentCardCount(textview_expandcard_count)
    }

    override fun onCreateToolbar(): Int {
        return R.id.stormtoolbar_expandcard
    }

    override fun onCreateViewPagerLayout(): Int {
        return 0//R.id.include_expandcard_viewpager
    }

    override fun onCreateApplyButton(): Int {
        return R.id.stormbutton_expandcard_apply
    }

    override fun onCreateMemoEditText(): Int {
        return R.id.edittext_expandcard_memo
    }

    override fun onCreateViewpager(): Int {
        return R.id.viewpager_expandcard
    }

    var allCardCount = 0

    private fun selectedCardCount(textView: TextView) {
        allCardCount = data!!.size

        if (data != null) {
            for (i in data!!.indices) {
                if (data!![i].cardIdx == cardIdx) {
                    currentPage = i

                    textView.text = getCountString(allCardCount, currentPage)
                }
            }
        }
    }

    private fun currentCardCount(textView: TextView) {
        viewpager.run {
            adapter = expandCardAdapter
            offscreenPageLimit = 3
            setPageTransformer(DepthPageTransformer())
            currentItem = currentPage

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPage = position

                    textView.text = getCountString(allCardCount, currentPage)
                }
            })
        }
    }

    private fun getCountString(allCount: Int, currentCount: Int): String {
        val cardCount = StringBuilder()
        cardCount.append("(")
            .append((currentCount+1).toString())
            .append("/")
            .append(allCount.toString())
            .append(")")

        return cardCount.toString()
    }

}

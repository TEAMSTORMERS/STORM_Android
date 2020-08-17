package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R

import com.stormers.storm.round.base.BaseExpandCardActivity
import kotlinx.android.synthetic.main.activity_expandcard.*

class RoundMeetingExpandActivity : BaseExpandCardActivity(false, R.layout.activity_expandcard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedCardCount(textview_card_number)
        currentCardCount(textview_card_number)
    }

    override fun onCreateToolbar(): Int {
        return R.id.stormtoolbar_expandcard
    }

    override fun onCreateViewPagerLayout(): Int {
        return R.id.include_expandcard_viewpager
    }

    override fun onCreateApplyButton(): Int {
        return R.id.stormbutton_expandcard_apply
    }

    override fun onCreateMemoEditText(): Int {
        return R.id.edittext_expandcard_memo
    }

    override fun onCreateViewpager(): Int {
        return R.id.viewpager_fragment_card_expand
    }

}

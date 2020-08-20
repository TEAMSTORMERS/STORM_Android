package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.round.base.BaseExpandCardActivity

class ParticipatedCardDetailActivity : BaseExpandCardActivity(false, R.layout.activity_participated_card_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateToolbar(): Int {
        return R.id.stormtoolbar_participatedcard
    }

    override fun onCreateViewPagerLayout(): Int {
        return R.id.include_participatedcard_viewpager
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
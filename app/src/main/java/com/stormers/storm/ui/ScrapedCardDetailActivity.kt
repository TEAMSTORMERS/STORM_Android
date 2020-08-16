package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.round.base.BaseExpandCardActivity
import kotlinx.android.synthetic.main.activity_scrapcard.*

class ScrapedCardDetailActivity : BaseExpandCardActivity(false, R.layout.activity_scrapcard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateToolbar(): Int {
        return R.id.stormtoolbar_scrapcard
    }

    override fun onCreateViewPagerLayout(): Int {
        return R.id.include_scrapcard_viewpager
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
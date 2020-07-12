package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ScrapcardDetailFragment

class ScrapedCardDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)
        goToFragment(ScrapcardDetailFragment::class.java, null)

    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }
}
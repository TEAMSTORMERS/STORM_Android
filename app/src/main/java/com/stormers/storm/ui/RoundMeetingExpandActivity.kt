package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.ExpandCardAdapter
import kotlinx.android.synthetic.main.activity_round_meeting_expand.*

class RoundMeetingExpandActivity : BaseActivity() {

    private val expandCardAdapter: ExpandCardAdapter by lazy { ExpandCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_meeting_expand)
        viewpager_fragment_card_expand.adapter = expandCardAdapter


    }
}
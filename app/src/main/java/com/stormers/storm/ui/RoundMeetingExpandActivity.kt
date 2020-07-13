package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.ExpandCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.util.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_expandcard.*

class RoundMeetingExpandActivity : BaseActivity() {

    private val expandCardAdapter: ExpandCardAdapter by lazy { ExpandCardAdapter() }

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)
        viewpager_fragment_card_expand.run {
            adapter = expandCardAdapter
            offscreenPageLimit = 3
            setPageTransformer(DepthPageTransformer())
        }

        expandCardAdapter.addAll(savedCardRepository.getAll(1, 1))
    }
}

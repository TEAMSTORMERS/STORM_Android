package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.round.base.BaseExpandCardActivity

class ScrapedCardDetailActivity : BaseExpandCardActivity(true) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onApplied(cardId: Int, memo: String?) {
        //Todo: memo 서버로 전송
    }
}
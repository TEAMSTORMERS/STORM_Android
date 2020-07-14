package com.stormers.storm.ui

import android.os.Bundle
import com.stormers.storm.round.base.BaseExpandCardActivity

class RoundMeetingExpandActivity : BaseExpandCardActivity(false) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onApplied(cardId: Int, memo: String?) {
        //Todo: 서버로 전송
    }
}

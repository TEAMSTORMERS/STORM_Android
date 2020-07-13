package com.stormers.storm.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ScrapcardDetailFragment
import kotlinx.android.synthetic.main.activity_round_progress.*

class ScrapedCardDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)
        goToFragment(ScrapcardDetailFragment::class.java, null)

        button_save_round_progress.visibility = View.VISIBLE
        button_save_round_progress.setOnClickListener {
            Toast.makeText(this, "카드가 추가되었습니다", Toast.LENGTH_SHORT).show()
        }

    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
    }
}
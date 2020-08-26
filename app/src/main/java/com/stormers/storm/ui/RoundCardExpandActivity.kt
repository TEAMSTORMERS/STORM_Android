package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.ExpandRoundCardAdapter
import com.stormers.storm.card.fragment.ExpandRoundCardFragment
import com.stormers.storm.ui.RoundListActivity.Companion.RESULT_DIRTY
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class RoundCardExpandActivity : BaseActivity(), ExpandRoundCardAdapter.OnScrapChangedCallback {

    companion object {
        private const val TAG = "RoundCardExpandActivity"
    }

    var isEdited = false

    private var selectedRoundIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        stormtoolbar_expandcard.setBackButton(View.OnClickListener { returnResult() })

        selectedRoundIdx = intent.getIntExtra("roundIdx", -1)
        val selectedCardIdx = intent.getIntExtra("cardIdx", -1)
        val selectedProjectName = intent.getStringExtra("projectName")
        val selectedProjectIdx = intent.getIntExtra("projectIdx", -1)

        val roundNumber = intent.getIntExtra("roundNumber", -1)
        val roundPurpose = intent.getStringExtra("roundPurpose")
        val roundTime = intent.getIntExtra("roundTime", -1)

        setRoundData(roundNumber, roundPurpose!!, roundTime)

        initView(selectedProjectName)

        goToFragment(ExpandRoundCardFragment::class.java, Bundle().apply {
            if (selectedCardIdx != -1) {
                putInt("cardIdx", selectedCardIdx)
            }
            if (selectedRoundIdx != -1) {
                putInt("roundIdx", selectedRoundIdx)
            }
            if (selectedCardIdx != -1) {
                putInt("projectIdx", selectedProjectIdx)
            }
        })
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_expandcard_fragment
    }

    override fun onScrapChanged() {
        isEdited = true
    }

    private fun initView(projectName: String?) {
        textview_expandcard_count.visibility = View.GONE

        textview_expandcard_projectname.text = projectName
    }

    private fun setRoundData(roundNumber: Int, roundPurpose: String, roundTime: Int) {
        textview_expandcard_roundnumber.text = StringBuilder("Round ")
            .append(roundNumber).toString()

        textview_expandcard_roundpurpose.text = roundPurpose

        textview_expandcard_roundtime.text = StringBuilder("총 ")
            .append(roundTime).append("분 소요").toString()
    }

    override fun onBackPressed() {
        returnResult()
    }

    private fun returnResult() {
        if (isEdited) {
            val intent = Intent()
            intent.putExtra("roundIdx", selectedRoundIdx)
            setResult(RESULT_DIRTY, intent)
        }
        finish()
    }
}
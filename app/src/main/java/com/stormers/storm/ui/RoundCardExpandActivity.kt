package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.fragment.ExpandCardFragment
import com.stormers.storm.round.data.source.RoundRepository
import com.stormers.storm.round.model.RoundModel
import kotlinx.android.synthetic.main.activity_expandcard.*
import java.lang.StringBuilder

class RoundCardExpandActivity : BaseActivity() {

    companion object {
        private const val TAG = "RoundCardExpandActivity"
    }

    private val roundRepository: RoundRepository by lazy { RoundRepository.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        val selectedCardIdx = intent.getIntExtra("cardIdx", -1)
        val selectedProjectName = intent.getStringExtra("projectName")
        val selectedRoundIdx = intent.getIntExtra("roundIdx", -1)

        initView(selectedProjectName)

        goToFragment(ExpandCardFragment::class.java, Bundle().apply {
            if (selectedCardIdx != -1) {
                putInt("cardIdx", selectedCardIdx)
            }
            if (selectedRoundIdx != -1) {
                putInt("roundIdx", selectedRoundIdx)
            }
        })

        roundRepository.get(selectedRoundIdx, object : RoundRepository.GetRoundCallback {
            override fun onRoundLoaded(round: RoundModel) {
                setRoundData(round)
            }

            @SuppressLint("LongLogTag")
            override fun onDataNotAvailable() {
                Log.e(TAG, "getRoundData: No round data. roundIdx ($selectedRoundIdx)")
            }
        })
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_expandcard_fragment
    }

    private fun initView(projectName: String?) {
        textview_expandcard_count.visibility = View.GONE

        textview_expandcard_projectname.text = projectName
    }

    private fun setRoundData(roundModel: RoundModel) {
        textview_expandcard_roundnumber.text = StringBuilder("Round ")
            .append(roundModel.roundNumber).toString()

        textview_expandcard_roundpurpose.text = roundModel.roundPurpose

        textview_expandcard_roundtime.text = StringBuilder("총 ")
            .append(roundModel.roundTime).append("분 소요").toString()
    }
}
package com.stormers.storm.card.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.roundmeeting.recyclerview.RoundmeetingAdapter
import com.stormers.storm.roundmeeting.recyclerview.RoundmeetingData
import kotlinx.android.synthetic.main.fragment_roundmeeting.*

class RoundmeetingFragment : Fragment() {

    lateinit var roundmeetingAdapter: RoundmeetingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roundmeeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundmeetingAdapter = RoundmeetingAdapter()
        RecyclerView_added_card_roundmeeting.adapter = roundmeetingAdapter
        loadRoundmeetingDatas()
    }

    private fun loadRoundmeetingDatas() {

        val datas = mutableListOf<RoundmeetingData>()

        datas.apply {
            add(
                RoundmeetingData(
                    //Todo: 더미데이터 (카드 상세 이미지가 들어갈 공간입니다)
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
            add(
                RoundmeetingData(
                    ImageView_added_card_roundmeeting = context?.getDrawable(R.drawable.storm_logo_example)
                )
            )
        }

        roundmeetingAdapter.addAll(datas)
    }

}
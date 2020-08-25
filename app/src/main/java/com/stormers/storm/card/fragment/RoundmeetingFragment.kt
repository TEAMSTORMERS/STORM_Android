package com.stormers.storm.card.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.model.CardWithOwnerModel
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.network.RequestCard
import com.stormers.storm.card.network.ResponseCardData
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundFinishCardExpandActivity
import kotlinx.android.synthetic.main.fragment_roundmeeting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundMeetingFragment : BaseFragment(R.layout.fragment_roundmeeting) {

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(
        CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private lateinit var roundMeetingListAdapter: CardListAdapter

    private val projectIdx = GlobalApplication.currentProject!!.projectIdx
    private val roundIdx = GlobalApplication.currentRound!!.roundIdx

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundMeetingListAdapter = CardListAdapter(object: CardListAdapter.OnCardClickListener {
            override fun onCardClick(cardIdx: Int) {
                val intent = Intent(context, RoundFinishCardExpandActivity::class.java)
                intent.putExtra("cardIdx", cardIdx)
                startActivity(intent)
            }
        })

        RecyclerView_added_card_roundmeeting.adapter = roundMeetingListAdapter
    }

    override fun onResume() {
        super.onResume()
        requestCards(projectIdx, roundIdx, GlobalApplication.userIdx)
    }

    private fun requestCards(projectIdx: Int, roundIdx: Int, userIdx: Int) {
        cardRepository.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, userIdx,
            object : CardDataSource.GetCardCallback<RoundInfoWithCardsModel> {

                override fun onCardLoaded(card: RoundInfoWithCardsModel) {
                    roundMeetingListAdapter.setList(card.cardWithOwnerList)
                }

                override fun onDataNotAvailable() {
                    Log.d(TAG, "No card in DB")
                }
            }
        )
    }
}
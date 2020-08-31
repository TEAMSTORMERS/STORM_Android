package com.stormers.storm.card.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.round.base.BaseRoundFinishActivity
import com.stormers.storm.round.base.BaseRoundFinishActivity.Companion.REQUEST_EXPAND
import com.stormers.storm.round.base.BaseRoundFragment
import com.stormers.storm.ui.RoundFinishRoundCardExpandActivity
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_roundmeeting.*

class RoundMeetingFragment : BaseRoundFragment(R.layout.fragment_roundmeeting) {

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(
        CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private var scrollPosition: Int = 0

    private lateinit var roundMeetingListAdapter: CardListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundMeetingListAdapter = CardListAdapter(object: CardListAdapter.OnCardClickListener {
            override fun onCardClick(cardIdx: Int) {
                val intent = Intent(context, RoundFinishRoundCardExpandActivity::class.java)
                intent.putExtra("cardIdx", cardIdx)
                scrollPosition =
                    (RecyclerView_added_card_roundmeeting.layoutManager as GridLayoutManager).findFirstVisibleItemPosition() + 4
                startActivityForResult(intent, REQUEST_EXPAND)
            }
        })

        RecyclerView_added_card_roundmeeting.run {
            adapter = roundMeetingListAdapter
            addItemDecoration(MarginDecoration(context, 2, 20, 20))
        }

        requestCards(projectIdx, roundIdx!!, userIdx)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_EXPAND && resultCode == BaseRoundFinishActivity.RESULT_DIRTY) {

            requestCards(projectIdx, roundIdx!!, userIdx)
        }
    }

    private fun requestCards(projectIdx: Int, roundIdx: Int, userIdx: Int) {
        showLoadingDialog()
        cardRepository.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, userIdx,
            object : CardDataSource.GetCardCallback<RoundInfoWithCardsModel> {

                override fun onCardLoaded(card: RoundInfoWithCardsModel) {
                    dismissLoadingDialog()
                    roundMeetingListAdapter.setList(card.cardWithOwnerList)
                    RecyclerView_added_card_roundmeeting.scrollToPosition(scrollPosition)
                }

                override fun onDataNotAvailable() {
                    dismissLoadingDialog()
                    Log.d(TAG, "No card in DB")
                }
            }
        )
    }
}
package com.stormers.storm.card.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.CardType
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.model.ResponseCardModel
import com.stormers.storm.card.model.CardEntity
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.network.RequestCard
import com.stormers.storm.card.network.ResponseCardData
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundMeetingExpandActivity
import kotlinx.android.synthetic.main.fragment_roundmeeting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundMeetingFragment : BaseFragment(R.layout.fragment_roundmeeting) {

    private val cardRepository : CardRepository by lazy { CardRepository() }
    private lateinit var roundMeetingListAdapter: CardListAdapter

    private val projectIdx = GlobalApplication.currentProject!!.projectIdx
    private val roundIdx = GlobalApplication.currentRound!!.roundIdx

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestCards()

        roundMeetingListAdapter = CardListAdapter(true, object: CardListAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(context, RoundMeetingExpandActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)
                startActivity(intent)
            }
        })

        RecyclerView_added_card_roundmeeting.adapter = roundMeetingListAdapter
    }

    private fun requestCards() {
        Log.d(TAG, "requestCards: projectIdx: $projectIdx, roundIdx: $roundIdx, userIdx: ${GlobalApplication.userIdx}")

        RetrofitClient.create(RequestCard::class.java).requestCard(projectIdx, roundIdx, GlobalApplication.userIdx)
            .enqueue(object : Callback<ResponseCardData> {
                override fun onFailure(call: Call<ResponseCardData>, t: Throwable) {
                    Log.d(TAG, "requestCards: fail : ${t.message}")
                }

                override fun onResponse(call: Call<ResponseCardData>, response: Response<ResponseCardData>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d(TAG, "requestCards: Success, ${response.body()!!.data.card_list}")
                            saveCard(response.body()!!.data.card_list)
                        } else {
                            Log.d(TAG, "requestCards: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "requestCards: Not success, ${response.message()}")
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        refreshCard()
    }

    private fun saveCard(cardList: List<ResponseCardModel>) {
        for (card in cardList) {
            val localCard = when {
                card.card_txt != null -> {
                    CardEntity(card.card_idx, projectIdx, roundIdx, card.user_idx,
                        CardEntity.FALSE, CardEntity.TEXT, card.card_txt, null)
                }
                card.card_img != null -> {
                    CardEntity(card.card_idx, projectIdx, roundIdx, card.user_idx,
                        CardEntity.FALSE, CardEntity.DRAWING, card.card_img, null)
                }
                else -> {
                    null
                }
            }

            if (localCard == null) {
                Log.d(TAG, "Wrong card")
                return
            }
            cardRepository.insert(localCard)

            //어뎁터에 추가
            localCard.let {
                val isScraped = CardType.scrapConverter(it.isScraped)
                val cardType = CardType.typeConverter(it.cardType)
                roundMeetingListAdapter.add(CardEnumModel(it.cardIdx, it.projectIdx, it.roundIdx, isScraped, cardType, it.content))
            }
        }
    }

    private fun refreshCard() {
        cardRepository.getAllForList(projectIdx, roundIdx, object: CardRepository.LoadEnumCardsCallback {
            override fun onCardLoaded(cards: List<CardEnumModel>) {
                roundMeetingListAdapter.setList(cards)
            }

            override fun onDataNotAvailable() {
                Log.d(TAG, "No card in DB")
            }
        })
    }
}
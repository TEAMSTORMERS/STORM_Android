package com.stormers.storm.card.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.model.ResponseCardModel
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.network.RequestCard
import com.stormers.storm.card.network.ResponseCardData
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundMeetingExpandActivity
import kotlinx.android.synthetic.main.fragment_roundmeeting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundMeetingFragment : BaseFragment(R.layout.fragment_roundmeeting) {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository() }
    private lateinit var roundMeetingAdapter: SavedCardAdapter

    private val projectIdx = GlobalApplication.currentProject!!.projectIdx
    private val roundIdx = GlobalApplication.currentRound!!.roundIdx

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestCards()

        roundMeetingAdapter = SavedCardAdapter(true, object: SavedCardAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(context, RoundMeetingExpandActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)
                startActivity(intent)
            }
        })

        RecyclerView_added_card_roundmeeting.adapter = roundMeetingAdapter
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
        showCard()
    }

    private fun saveCard(cardList: List<ResponseCardModel>) {
        for (card in cardList) {
            val localCard = when {
                card.card_txt != null -> {
                    SavedCardEntity(card.card_idx, projectIdx, roundIdx, card.user_idx,
                        SavedCardEntity.FALSE, SavedCardEntity.TEXT, card.card_txt, null)
                }
                card.card_img != null -> {
                    SavedCardEntity(card.card_idx, projectIdx, roundIdx, card.user_idx,
                        SavedCardEntity.FALSE, SavedCardEntity.DRAWING, card.card_img, null)
                }
                else -> {
                    null
                }
            }

            if (localCard == null) {
                Log.d(TAG, "Wrong card")
                return
            }
            savedCardRepository.insert(localCard)

            //어뎁터에 추가
            roundMeetingAdapter.add(localCard)
        }
    }

    private fun showCard() {
        val data = savedCardRepository.getAll(projectIdx, roundIdx)
        if (data != null) {
            roundMeetingAdapter.setList(data)
        } else {
            Log.d(TAG, "No card in DB")
        }
    }
}
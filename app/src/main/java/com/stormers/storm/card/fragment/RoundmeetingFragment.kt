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
import com.stormers.storm.ui.RoundMeetingExpandActivity
import kotlinx.android.synthetic.main.fragment_roundmeeting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundmeetingFragment : BaseFragment(R.layout.fragment_roundmeeting) {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }
    lateinit var roundmeetingAdapter: SavedCardAdapter

    private val projectIdx = preference.getProjectIdx()
    private val roundIdx = preference.getRoundIdx()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (projectIdx == null || roundIdx == null) {
            Log.d("RoundMeettingFragment", "projectIdx or roundIdx is null")
            return
        }

        savedCardRepository.delete(projectIdx, roundIdx)

        RetrofitClient.create(RequestCard::class.java).requestCard(projectIdx, roundIdx)
            .enqueue(object : Callback<ResponseCardData> {
                override fun onFailure(call: Call<ResponseCardData>, t: Throwable) {
                    Log.d("RequestCard", "fail : ${t.message}")
                }

                override fun onResponse(call: Call<ResponseCardData>, response: Response<ResponseCardData>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            saveCard(response.body()!!.data.card_list)
                            showCard()
                        }
                    }
                }
            })

        roundmeetingAdapter = SavedCardAdapter(true, object: SavedCardAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(context, RoundMeetingExpandActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)
                startActivity(intent)
            }
        })

        RecyclerView_added_card_roundmeeting.adapter = roundmeetingAdapter
    }

    override fun onResume() {
        super.onResume()
        showCard()
    }

    private fun saveCard(cardList: List<ResponseCardModel>) {
        for (card in cardList) {
            val localCard = if (card.card_txt != null) {
                SavedCardEntity(projectIdx!!, roundIdx!!, SavedCardEntity.FALSE, SavedCardEntity.TEXT, card.card_txt, null)
            } else {
                SavedCardEntity(projectIdx!!, roundIdx!!, SavedCardEntity.FALSE, SavedCardEntity.DRAWING, card.card_img, null)
            }
            savedCardRepository.insert(localCard)
        }
    }

    private fun showCard() {
        val data = savedCardRepository.getAll(projectIdx!!, roundIdx!!)
        roundmeetingAdapter.run {
            clear()
            addAll(data)
        }
    }
}
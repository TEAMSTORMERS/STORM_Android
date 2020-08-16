package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.adapter.RoundListAdapterForViewPager
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.round.network.response.ResponseFinalRoundData
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundListActivity : AppCompatActivity() {

    private var roundIdx = -1
    lateinit var roundListAdapterForViewPager: RoundListAdapterForViewPager

    private lateinit var cardAdapter: SavedCardAdapter

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1

    private var roundNo = -1

    private lateinit var retrofitClient: RequestRound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)
        projectIdx = intent.getIntExtra("projectIdx", 1)
        roundIdx = intent.getIntExtra("roundIdx", 1)
        roundNo = intent.getIntExtra("roundNo", 1)

        roundListAdapterForViewPager = RoundListAdapterForViewPager()

        cardAdapter = SavedCardAdapter(true, object : SavedCardAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(this@RoundListActivity, ScrapedCardDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)

                startActivity(intent)
            }
        })

        retrofitClient = RetrofitClient.create(RequestRound::class.java)

        retrofitClient.responseFinalRoundData(projectIdx).enqueue(object : Callback<ResponseFinalRoundData> {
            override fun onFailure(call: Call<ResponseFinalRoundData>, t: Throwable) {
                if (t.message != null){
                    Log.d("RoundListActivity", t.message!!)
                } else {
                    Log.d("RoundListActivity", "통신실패")
                }
            }

            override fun onResponse(call: Call<ResponseFinalRoundData>, response: Response<ResponseFinalRoundData>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        for (i in response.body()!!.data.indices) {
                            Log.d("RoundListActivity", "받아온 라운드 정보 : ${response.body()!!.data[i]}")
                        }
                        roundListAdapterForViewPager.addAll(response.body()!!.data)
                        Log.d("roundIdx" , roundIdx.toString())
                    }
                    else {
                        Log.d("RoundListActivity", "통신실패")
                    }
                } else {
                    Log.d("RoundListActivity", "${response.message()} , ${response.errorBody()}")
                }
            }
        })



        recyclerView_roundcardlist_cardlist.run {
            adapter = cardAdapter
            addItemDecoration(MarginDecoration(this@RoundListActivity, 2, 20, 20))
        }

        viewpager_roundcardlist_round.run {
            adapter = roundListAdapterForViewPager
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            currentItem = roundNo - 1
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val roundIdx = roundListAdapterForViewPager.getItem(position).roundIdx

                    val data = savedCardRepository.getAll(projectIdx, roundIdx)

                    cardAdapter.clear()
                    cardAdapter.addAll(data)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()

        cardAdapter.clear()
        cardAdapter.addAll(savedCardRepository.getAll(projectIdx, roundIdx))
    }
}
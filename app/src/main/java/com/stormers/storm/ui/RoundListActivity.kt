package com.stormers.storm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.adapter.RoundListAdapterForViewPager
import com.stormers.storm.round.network.FinalRoundInterface
import com.stormers.storm.round.network.ResponseFinalRoundData
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundListActivity : AppCompatActivity() {

    private var roundIdx = -1
    lateinit var roundListAdapterForViewPager: RoundListAdapterForViewPager

    private val cardAdapter: SavedCardAdapter by lazy { SavedCardAdapter(true, null) }

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1
    private lateinit var retrofitClient: FinalRoundInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)
        projectIdx = intent.getIntExtra("projectIdx", 1)
        roundIdx = intent.getIntExtra("roundIdx", 1)

        roundListAdapterForViewPager = RoundListAdapterForViewPager()

        retrofitClient = RetrofitClient.create(FinalRoundInterface::class.java)

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
                        viewpager_roundcardlist_round.currentItem = roundIdx
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
            //currentItem = 5
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //Todo: roundIdx가 사용된 부분을 round_number를 이용할 수 있도록 변경해야 합니다 !
                    val roundIdx = roundListAdapterForViewPager.getItem(position).roundIdx

                    //Todo: projectIdx 도 인텐트로 받아오기
                    val data = savedCardRepository.getAll(1, roundIdx)


                    cardAdapter.clear()
                    cardAdapter.addAll(data)
                }
            })
        }



        cardAdapter.addAll(savedCardRepository.getAll(1, 1))


    }

}
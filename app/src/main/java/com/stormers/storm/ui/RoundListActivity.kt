package com.stormers.storm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.network.ProjectInterface
import com.stormers.storm.round.adapter.RoundListAdapterForViewPager
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.round.network.FinalRoundInterface
import com.stormers.storm.round.network.ResponseFinalRoundData
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoundListActivity : AppCompatActivity() {

    lateinit var roundListAdapterForViewPager: RoundListAdapterForViewPager

    private val cardAdapter: SavedCardAdapter by lazy { SavedCardAdapter(true, null) }

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(application) }

    private var projectIdx = -1
    private lateinit var retrofitClient: FinalRoundInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)
        projectIdx = intent.getIntExtra("projectIdx", 1)

        roundListAdapterForViewPager = RoundListAdapterForViewPager()

        retrofitClient = RetrofitClient.create(FinalRoundInterface::class.java)

        retrofitClient.responseFinalRoundData(projectIdx.toString()).enqueue(object : Callback<ResponseFinalRoundData> {
            override fun onFailure(call: Call<ResponseFinalRoundData>, t: Throwable) {
                if (t.message != null){
                    Log.d("RoundListActivity", t.message!!)
                } else {
                    Log.d("RoundListActivity", "통신실패")
                }
            }

            override fun onResponse(
                call: Call<ResponseFinalRoundData>,
                response: Response<ResponseFinalRoundData>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        for (i in response.body()!!.data.indices) {
                            Log.d("RoundListActivity", "받아온 라운드 정보 : ${response.body()!!.data[i]}")
                            }
                        //roundListAdapterForViewPager.addAll(response.body()!!.data)
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

        cardAdapter.addAll(savedCardRepository.getAll(1, 1))

        viewpager_roundcardlist_round.run {
            adapter = roundListAdapterForViewPager
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val roundIdx = roundListAdapterForViewPager.getItem(position).roundIdx

                    //Todo: projectIdx 도 인텐트로 받아오기
                    val data = savedCardRepository.getAll(1, roundIdx)

                    cardAdapter.clear()
                    cardAdapter.addAll(data)
                }
            })
        }
    }

    //Dummy
    /*private fun loadRoundDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(RoundDescriptionModel(null, null, "베개와 유리병의 공통점은?", "11분 소요", 0, 1))
            add(RoundDescriptionModel(null, null, "Pillow 와 Glass 의 공통점은?", "11분 소요", 1, 1))
            add(RoundDescriptionModel(null, null, "평화와 희원이의 공통점은?", "11분 소요", 2, 1))
        }

        return datas
    }*/
}
package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.adapter.ScrapedCardListAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_scrapcard_collecting.*

class ScrapCardCollectingActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ScrapCardCollectingActivity"
    }

    lateinit var scrapCollectListAdapter: ScrapedCardListAdapter

    private val cardRepository : CardRepository by lazy {
        CardRepository.getInstance(CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private var projectIdx = -1

    private var projectName: String? = null

    private val userIdx = GlobalApplication.userIdx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrapcard_collecting)

        stormtoolbar_scarpedcard.run {
            setBackButton()
            setMyPageButton()
        }

        //필요한 세팅값 불러오기
        projectIdx = intent.getIntExtra("projectIdx", -1)
        projectName = intent.getStringExtra("projectName")

        //불러온 세팅값 반영
        textView_project_name_scrap_collect.text = projectName

        initRecyclerView()
    }

    private fun initRecyclerView() {

        //카드 리사이클러뷰 어댑터 초기화
        scrapCollectListAdapter = ScrapedCardListAdapter(true, object: ScrapedCardListAdapter.OnCardClickListener {
            override fun onCardClick(cardIdx: Int) {
                val intent = Intent(this@ScrapCardCollectingActivity, ScrapedRoundCardExpandActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("cardIdx", cardIdx)
                intent.putExtra("projectName", projectName)
                startActivity(intent)
            }
        })

        //스크랩한 카드 리사이클러뷰 초기화
        RecyclerView_scrap_collect.run {
            adapter = scrapCollectListAdapter
            addItemDecoration(MarginDecoration(this@ScrapCardCollectingActivity, 2, 20, 20))
        }
    }

    override fun onResume() {
        super.onResume()

        //스크랩한 카드가 변경될 수 있으므로 여기서 갱신
        setScrapedCardList()
    }

    private fun setScrapedCardList() {
        cardRepository.getScrapedCardsWithInfo(projectIdx, userIdx, object: CardDataSource.GetCardCallback<ScrapedCardModel> {

            //카드를 성공적으로 불러왔을 때
            override fun onCardLoaded(card: ScrapedCardModel) {
                scrapCollectListAdapter.setList(card.cardItem)

                //총 몇개의 카드인지 초기화
                textView_card_count_scrap_collect.text = StringBuilder("총 ")
                    .append(card.cardItem.size).append("개의 카드").toString()
            }

            @SuppressLint("LongLogTag")
            override fun onDataNotAvailable() {
                Log.e(TAG, "No data in DB. projectIdx: $projectIdx")
            }
        })
    }

}
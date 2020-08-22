package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardListAdapter
import com.stormers.storm.card.model.CardEnumModel
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.customview.dialog.StormDialog.Companion.TAG
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_detail.*
import kotlinx.android.synthetic.main.activity_scrapcard_collecting.*

class ScrapCardCollectingActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ScrapCardCollectingActivity"
    }

    lateinit var scrapCollectListAdapter: CardListAdapter

    private val cardRepository: CardRepository by lazy { CardRepository() }

    private var projectIdx = -1

    private var projectName: String? = null

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
        initScrapedCardCountInfo()

        getScrapedCard()

    }

    private fun getScrapedCard() {
        //카드 리사이클러뷰 어댑터 초기화
        scrapCollectListAdapter = CardListAdapter(true, object: CardListAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardIdx: Int) {
                val intent = Intent(this@ScrapCardCollectingActivity, ScrapedCardExpandActivity::class.java)
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
        cardRepository.getScrapAllForList(projectIdx, object: CardRepository.LoadCardModel<CardEnumModel> {
            override fun onCardsLoaded(cards: List<CardEnumModel>) {
                scrapCollectListAdapter.setList(cards)
            }

            @SuppressLint("LongLogTag")
            override fun onDataNotAvailable() {
                Log.e(TAG, "No data in DB. projectIdx: $projectIdx")
            }
        })
    }

    private fun initScrapedCardCountInfo() {
        val cardCount = cardRepository.getScrapAll(projectIdx).size
        textView_card_count_scrap_collect.text = StringBuilder("총 ")
            .append(cardCount).append("개의 카드").toString()
    }

}
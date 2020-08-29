package com.stormers.storm.card.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.adapter.ExpandScrapedCardAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.CardMemoModel
import com.stormers.storm.card.model.ScrapedCardModel
import com.stormers.storm.card.model.ScrapedCardWithRoundInfo
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_expand_card.*

class ExpandScrapedCardFragment: BaseFragment(R.layout.fragment_expand_card) {

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(
        CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private val expandScrapedCardAdapter: ExpandScrapedCardAdapter by lazy { ExpandScrapedCardAdapter() }

    private var currentPage = 0

    private lateinit var cardViewPager: ViewPager2

    private var scrapedCardChangeCallback: OnScrapedCardPageChangeCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //프래그먼트를 소유한 액티비티가 콜백을 구현했는지 확인하고 초기화
            scrapedCardChangeCallback = context as OnScrapedCardPageChangeCallback
        } catch (e: ClassCastException) {
            Log.d(TAG, "onAttach: Activity doesn't have CardPageCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardViewPager = viewpager_expandcard

        val selectedCardIdx = arguments?.getInt("cardIdx")
        val projectIdx = arguments?.getInt("projectIdx")
        val userIdx = GlobalApplication.userIdx

        if (selectedCardIdx == null || projectIdx == null) {
            Log.e(TAG, "onViewCreated: Wrong access.")
            return
        }

        cardRepository.getScrapedCardsWithInfo(projectIdx, userIdx, object: CardDataSource.GetCardCallback<ScrapedCardModel> {
            override fun onCardLoaded(card: ScrapedCardModel) {
                expandScrapedCardAdapter.setList(card.cardItem)

                //현재 페이지 초기화
                initCurrentPage(selectedCardIdx, card.cardItem)

                //뷰페이저 초기화
                initViewPager(card.cardItem)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "onViewCreated: No cards data. project : $projectIdx")
            }
        })

        //저장 버튼 초기화
        stormbutton_expandcard_apply.setOnClickListener {
            if (expandScrapedCardAdapter.itemCount == 0) {
                Log.d(TAG, "applyButton : Card Initializing is not yet.")
                return@setOnClickListener
            }

            val currentMemo = edittext_expandcard_memo.text.toString()
            val currentCard = expandScrapedCardAdapter.getItem(currentPage)
            val cardMemoModel = CardMemoModel(userIdx, currentCard.cardIdx, currentMemo)

            if (currentCard.cardMemo == null) {
                currentCard.cardMemo = currentMemo
                cardRepository.createMemo(cardMemoModel)
                Toast.makeText(context, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            } else if (currentCard.cardMemo != currentMemo) {
                currentCard.cardMemo = currentMemo
                cardRepository.updateMemo(cardMemoModel)
                Toast.makeText(context, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        imagebutton_before_card.setOnClickListener {
            cardViewPager.run {
                currentItem -= 1
            }
        }

        imagebutton_next_card.setOnClickListener {
            cardViewPager.run {
                currentItem += 1
            }
        }
    }

    private fun initViewPager(data: List<ScrapedCardWithRoundInfo>) {
        cardViewPager.run {
            adapter = expandScrapedCardAdapter
            offscreenPageLimit = 3
            setPageTransformer(DepthPageTransformer())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setVisibilityOfMoveButton(position, data.size)

                    currentPage = position
                    scrapedCardChangeCallback?.onCardPageChanged(position, expandScrapedCardAdapter.itemCount, data[position])
                    setMemo(position, data)
                }
            })
            currentItem = currentPage
        }
    }

    private fun setVisibilityOfMoveButton(position: Int, length: Int) {
        imagebutton_before_card.visibility = View.VISIBLE
        imagebutton_next_card.visibility = View.VISIBLE

        if (position == 0) {
            imagebutton_before_card.visibility = View.GONE
        }
        if (position == length - 1) {
            imagebutton_next_card.visibility = View.GONE
        }
    }

    private fun initCurrentPage(cardIdx: Int?, data: List<ScrapedCardWithRoundInfo>) {
        if (cardIdx == null) {
            currentPage = 0
        } else {
            for (i in data.indices) {
                if (data[i].cardIdx == cardIdx) {
                    currentPage = i
                    scrapedCardChangeCallback?.onCardPageChanged(i, expandScrapedCardAdapter.itemCount, data[i])
                    break
                }
            }
        }
        setMemo(currentPage, data)
    }

    private fun setMemo(position: Int, data: List<ScrapedCardWithRoundInfo>) {
        edittext_expandcard_memo.setText(data[position].cardMemo)
    }

    interface OnScrapedCardPageChangeCallback {
        fun onCardPageChanged(position: Int, totalCount: Int, card: ScrapedCardWithRoundInfo)
    }
}
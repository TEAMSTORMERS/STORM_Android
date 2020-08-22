package com.stormers.storm.card.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.adapter.ExpandCardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.repository.CardRepository
import com.stormers.storm.util.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_expand_card.*

class ExpandCardFragment: BaseFragment(R.layout.fragment_expand_card) {

    private val cardRepository: CardRepository by lazy { CardRepository.getInstance() }

    private val expandCardAdapter: ExpandCardAdapter by lazy { ExpandCardAdapter() }

    private var currentPage = 0

    lateinit var cardViewPager: ViewPager2

    private var cardChangeCallback: OnCardPageChangeCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //프래그먼트를 소유한 액티비티가 콜백을 구현했는지 확인하고 초기화
            cardChangeCallback = context as OnCardPageChangeCallback
        } catch (e: ClassCastException) {
            Log.d(TAG, "onAttach: Activity doesn't have CardPageCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardViewPager = viewpager_expandcard

        //필요한 값들 받아오기
        val selectedCardIdx = arguments?.getInt("cardIdx")
        val roundIdx = arguments?.getInt("roundIdx")
        val isScrapList = arguments?.getBoolean("scrapList")?: false
        val projectIdx = arguments?.getInt("projectIdx")

        //카드가 로딩되었을 때 콜백
        val callback = object : CardRepository.LoadCardModel<CardModel> {
            override fun onCardsLoaded(cards: List<CardModel>) {
                //뷰페이저 어댑터에 카드 초기화
                expandCardAdapter.setList(cards)

                //현재 페이지 초기화
                initCurrentPage(selectedCardIdx, cards)

                //뷰페이저 초기화
                initViewPager(cards)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "onViewCreated: No cards data. round: $roundIdx")
            }
        }

        //스크랩한 카드만 불러올지, 모든 카드를 불러올지 결정
        if (isScrapList) {
            if (projectIdx == null) {
                Log.e(TAG, "onViewCreated : projectIdx is null")
                return
            }
            cardRepository.getScrapAll(projectIdx, callback)
        } else {
            if (roundIdx == null) {
                Log.e(TAG, "onViewCreated : roundIdx is null")
                return
            }
            cardRepository.getAll(roundIdx, callback)
        }

        //저장 버튼 초기화
        stormbutton_expandcard_apply.setOnClickListener {
            if (expandCardAdapter.itemCount == 0) {
                Log.d(TAG, "applyButton : Card Initializing is not yet.")
                return@setOnClickListener
            }
            val currentCard = expandCardAdapter.getItem(currentPage)
            val currentMemo = edittext_expandcard_memo.text.toString()

            if (currentMemo != currentCard.cardMemo) {
                currentCard.cardMemo = currentMemo
                cardRepository.update(currentCard)
            }
        }
    }

    private fun initViewPager(data: List<CardModel>) {
        cardViewPager.run {
            adapter = expandCardAdapter
            offscreenPageLimit = 3
            setPageTransformer(DepthPageTransformer())
            currentItem = currentPage

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPage = position
                    cardChangeCallback?.onCardPageChanged(position, expandCardAdapter.itemCount)
                    setMemo(position, data)
                }
            })
        }
    }

    private fun initCurrentPage(cardIdx: Int?, data: List<CardModel>) {
        if (cardIdx == null) {
            currentPage = 0
        } else {
            for (i in data.indices) {
                if (data[i].cardIdx == cardIdx) {
                    currentPage = i
                    break
                }
            }
        }
        setMemo(currentPage, data)
    }

    private fun setMemo(position: Int, data: List<CardModel>) {
        edittext_expandcard_memo.setText(data[position].cardMemo)
    }

    interface OnCardPageChangeCallback {
        fun onCardPageChanged(position: Int, totalCount: Int)
    }
}
package com.stormers.storm.card.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.adapter.ExpandRoundCardAdapter
import com.stormers.storm.card.data.source.CardDataSource
import com.stormers.storm.card.data.source.CardRepository
import com.stormers.storm.card.data.source.local.CardLocalDataSource
import com.stormers.storm.card.data.source.remote.CardRemoteDataSource
import com.stormers.storm.card.model.CardMemoModel
import com.stormers.storm.card.model.CardWithOwnerModel
import com.stormers.storm.card.model.RoundInfoWithCardsModel
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_expand_card.*

class ExpandRoundCardFragment: BaseFragment(R.layout.fragment_expand_card) {

    private val cardRepository : CardRepository by lazy { CardRepository.getInstance(
        CardRemoteDataSource, CardLocalDataSource.getInstance()) }

    private lateinit var expandRoundCardAdapter: ExpandRoundCardAdapter

    private var currentPage = 0

    private lateinit var cardViewPager: ViewPager2

    private var roundCardChangeCallback: OnRoundCardPageChangeCallback? = null

    private var scrapChangedCallback: ExpandRoundCardAdapter.OnScrapChangedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //프래그먼트를 소유한 액티비티가 콜백을 구현했는지 확인하고 초기화
            roundCardChangeCallback = context as OnRoundCardPageChangeCallback
        } catch (e: ClassCastException) {
            Log.d(TAG, "onAttach: Activity doesn't have CardPageCallback")
        }
        try {
            scrapChangedCallback = context as ExpandRoundCardAdapter.OnScrapChangedCallback
        } catch (e: java.lang.ClassCastException) {
            Log.d(TAG, "onAttach: Activity doesn't have ScrapChangedCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardViewPager = viewpager_expandcard

        expandRoundCardAdapter = ExpandRoundCardAdapter()

        if (scrapChangedCallback != null) {
            expandRoundCardAdapter.setOnScrapChangedListener(scrapChangedCallback!!)
        }

        //필요한 값들 받아오기
        val selectedCardIdx = arguments?.getInt("cardIdx")
        val roundIdx = arguments?.getInt("roundIdx")
        val projectIdx = arguments?.getInt("projectIdx")
        val userIdx = GlobalApplication.userIdx

        if (selectedCardIdx == null || roundIdx == null || projectIdx == null) {
            Log.e(TAG, "onViewCreated: Wrong access.")
            return
        }

        cardRepository.getCardWithProjectAndRoundInfo(projectIdx, roundIdx, userIdx, object : CardDataSource.GetCardCallback<RoundInfoWithCardsModel> {

            override fun onCardLoaded(card: RoundInfoWithCardsModel) {
                expandRoundCardAdapter.setList(card.cardWithOwnerList)

                //현재 페이지 초기화
                initCurrentPage(selectedCardIdx, card.cardWithOwnerList)

                //뷰페이저 초기화
                initViewPager(card.cardWithOwnerList)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "onViewCreated: No cards data. round: $roundIdx")
            }
        })

        //저장 버튼 초기화
        stormbutton_expandcard_apply.setOnClickListener {
            if (expandRoundCardAdapter.itemCount == 0) {
                Log.d(TAG, "applyButton : Card Initializing is not yet.")
                return@setOnClickListener
            }

            val currentMemo = edittext_expandcard_memo.text.toString()
            val currentCard = expandRoundCardAdapter.getItem(currentPage)
            val cardMemoModel = CardMemoModel(userIdx, currentCard.cardIdx, currentMemo)

            if (currentCard.cardMemo == null) {
                currentCard.cardMemo = currentMemo
                cardRepository.createMemo(cardMemoModel)
                Toast.makeText(context, "메모가 저장되었습니다", Toast.LENGTH_SHORT).show()
            } else if (currentCard.cardMemo != currentMemo) {
                currentCard.cardMemo = currentMemo
                cardRepository.updateMemo(cardMemoModel)
                Toast.makeText(context, "메모가 저장되었습니다", Toast.LENGTH_SHORT).show()
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

    private fun initViewPager(data: List<CardWithOwnerModel>) {
        cardViewPager.run {
            adapter = expandRoundCardAdapter
            offscreenPageLimit = 3
            setPageTransformer(DepthPageTransformer())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setVisibilityOfMoveButton(position, data.size)

                    currentPage = position
                    roundCardChangeCallback?.onCardPageChanged(position, expandRoundCardAdapter.itemCount, data[position])
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

    private fun initCurrentPage(cardIdx: Int?, data: List<CardWithOwnerModel>) {
        if (cardIdx == null) {
            currentPage = 0
        } else {
            for (i in data.indices) {
                if (data[i].cardIdx == cardIdx) {
                    currentPage = i
                    roundCardChangeCallback?.onCardPageChanged(i, expandRoundCardAdapter.itemCount, data[i])
                    break
                }
            }
        }
        setMemo(currentPage, data)
    }

    private fun setMemo(position: Int, data: List<CardWithOwnerModel>) {
        edittext_expandcard_memo.setText(data[position].cardMemo)
    }

    interface OnRoundCardPageChangeCallback {
        fun onCardPageChanged(position: Int, totalCount: Int, card: CardWithOwnerModel)
    }
}
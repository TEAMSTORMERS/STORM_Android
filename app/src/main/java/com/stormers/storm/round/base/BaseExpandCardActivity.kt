package com.stormers.storm.round.base

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.ExpandCardAdapter
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.util.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_expandcard.*

abstract class BaseExpandCardActivity(private val isScraped: Boolean): BaseActivity() {
    private val expandCardAdapter: ExpandCardAdapter by lazy { ExpandCardAdapter() }

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    private var currentPage = 0

    private var data: List<SavedCardEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        val projectIdx = intent.getIntExtra("projectIdx", 1)
        val roundIdx = intent.getIntExtra("roundIdx", 1)
        val cardId = intent.getIntExtra("cardId", 0)

        data = initData(projectIdx, roundIdx, cardId)

        initCurrentPage(cardId)

        expandCardAdapter.addAll(data)

        initViewPager()

        stormbutton_expandcard_apply.setOnClickListener {

            val updatedCard = expandCardAdapter.getItem(currentPage)
            updatedCard.memo = edittext_expandcard_memo.text.toString()
            savedCardRepository.update(updatedCard)

            onApplied(updatedCard.cardId, updatedCard.memo)
        }

    }

    private fun initViewPager() {
        viewpager_fragment_card_expand.run {
            adapter = expandCardAdapter
            offscreenPageLimit = 3
            setPageTransformer(DepthPageTransformer())
            currentItem = currentPage

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPage = position

                    setMemo(position)
                }
            })
        }
    }

    private fun initCurrentPage(cardId: Int) {
        if (data != null) {
            for (i in data!!.indices) {
                if (data!![i].cardId == cardId) {
                    currentPage = i

                    setMemo(currentPage)
                    break
                }
            }
        }
    }

    private fun setMemo(position: Int) {
        data?.let {
            if (it[position].memo != null) {
                edittext_expandcard_memo.setText(it[position].memo)
            } else {
                edittext_expandcard_memo.text = null
            }
        }
    }

    private fun initData(projectIdx: Int, roundIdx: Int, cardId: Int): List<SavedCardEntity>? {
        return  if (isScraped) {
            savedCardRepository.getAllScrapedCard(projectIdx, roundIdx)
        } else {
            savedCardRepository.getAll(projectIdx, roundIdx)
        }
    }

    abstract fun onApplied(cardId: Int, memo: String?)
}
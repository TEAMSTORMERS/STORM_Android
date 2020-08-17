package com.stormers.storm.round.base

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.adapter.ExpandCardAdapter
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.StormToolbar
import com.stormers.storm.util.DepthPageTransformer

abstract class BaseExpandCardActivity(private val isScraped: Boolean, @LayoutRes val layoutRes: Int): BaseActivity() {
    private val expandCardAdapter: ExpandCardAdapter by lazy { ExpandCardAdapter() }

    private val savedCardRepository: SavedCardRepository by lazy { SavedCardRepository(application) }

    private var currentPage = 0

    private var data: List<SavedCardEntity>? = null

    private lateinit var toolbar: StormToolbar

    private lateinit var includeLayout: View

    private lateinit var applyButton: StormButton

    private lateinit var memoEditText: EditText

    private lateinit var viewpager: ViewPager2

    protected var projectIdx = -1

    protected var roundIdx = -1

    protected var cardId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        projectIdx = intent.getIntExtra("projectIdx", -1)

        roundIdx = intent.getIntExtra("roundIdx", -1)

        cardId = intent.getIntExtra("cardId", -1)

        toolbar = findViewById(onCreateToolbar())

        toolbar.setBackButton()

        includeLayout = findViewById(onCreateViewPagerLayout())

        applyButton = includeLayout.findViewById(onCreateApplyButton())

        memoEditText = includeLayout.findViewById(onCreateMemoEditText())

        viewpager = includeLayout.findViewById(onCreateViewpager())

        data = initData(projectIdx, roundIdx, cardId)

        initCurrentPage(cardId)

        expandCardAdapter.addAll(data)

        initViewPager()

        applyButton.setOnClickListener {

            val updatedCard = expandCardAdapter.getItem(currentPage)
            updatedCard.memo = memoEditText.text.toString()
            savedCardRepository.update(updatedCard)

            Toast.makeText(application, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun initViewPager() {
        viewpager.run {
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
                if (data!![i].cardIdx == cardId) {
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
                memoEditText.setText(it[position].memo)
            } else {
                memoEditText.text = null
            }
        }
    }

    private fun initData(projectIdx: Int, roundIdx: Int, cardId: Int): List<SavedCardEntity>? {
        return  if (isScraped) {
            savedCardRepository.getAllScrapedCard(projectIdx)
        } else {
            savedCardRepository.getAll(projectIdx, roundIdx)
        }
    }

    abstract fun onCreateToolbar() : Int

    abstract fun onCreateViewPagerLayout() : Int

    abstract fun onCreateApplyButton() : Int

    abstract fun onCreateMemoEditText() : Int

    abstract fun onCreateViewpager() : Int


}
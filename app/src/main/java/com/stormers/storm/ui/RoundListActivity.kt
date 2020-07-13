package com.stormers.storm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.round.adapter.RoundListAdapterForViewPager
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*

class RoundListActivity : AppCompatActivity() {

    lateinit var roundListAdapterForViewPager: RoundListAdapterForViewPager

    private val cardAdapter: SavedCardAdapter by lazy { SavedCardAdapter(true) }

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)

        roundListAdapterForViewPager = RoundListAdapterForViewPager()

        roundListAdapterForViewPager.addAll(loadRoundDatas())

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
    private fun loadRoundDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(RoundDescriptionModel(null, null, "베개와 유리병의 공통점은?", "11분 소요", 0))
            add(RoundDescriptionModel(null, null, "Pillow 와 Glass 의 공통점은?", "11분 소요", 1))
            add(RoundDescriptionModel(null, null, "평화와 희원이의 공통점은?", "11분 소요", 2))
        }

        return datas
    }
}
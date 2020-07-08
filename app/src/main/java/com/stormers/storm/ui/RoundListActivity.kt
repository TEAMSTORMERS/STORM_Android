package com.stormers.storm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.card.adapter.CardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.round.adapter.RoundListAdapterForViewPager
import com.stormers.storm.round.model.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*

class RoundListActivity : AppCompatActivity() {

    lateinit var roundListAdapterForViewPager: RoundListAdapterForViewPager

    private val cardAdapter: CardAdapter by lazy { CardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)

        roundListAdapterForViewPager = RoundListAdapterForViewPager()

        viewpager_roundcardlist_round.run {
            adapter = roundListAdapterForViewPager
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
        }

        roundListAdapterForViewPager.addAll(loadRoundDatas())

        recyclerView_roundcardlist_cardlist.run {
            adapter = cardAdapter
            //Fixme MarginDecoration 고쳐야겠다
            //Todo: 바깥쪽 여백도 줄 수 있도록 하기
            addItemDecoration(MarginDecoration(this@RoundListActivity, 2, 20, 20))
        }

        cardAdapter.addAll(loadCardListOfRound())
    }

    //Dummy
    private fun loadCardListOfRound(): MutableList<CardModel> {
        val data = mutableListOf<CardModel>()

        data.apply {
            add(CardModel("", false, null))
            add(CardModel("", true, null))
            add(CardModel("", false, null))
            add(CardModel("", false, null))
            add(CardModel("", true, null))
            add(CardModel("", false, null))
            add(CardModel("", true, null))
            add(CardModel("", false, null))
            add(CardModel("", false, null))
        }

        return data
    }

    //Dummy
    private fun loadRoundDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(RoundDescriptionModel(null, null, "베개와 유리병의 공통점은?", "11분 소요"))
            add(RoundDescriptionModel(null, null, "Pillow 와 Glass 의 공통점은?", "11분 소요"))
            add(RoundDescriptionModel(null, null, "평화와 희원이의 공통점은?", "11분 소요"))
        }

        return datas
    }
}
package com.stormers.storm.ui

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
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

        viewpager_roundcardlist_round.currentItem = intent.getIntExtra("roundIdx", 0) - 1

        recyclerView_roundcardlist_cardlist.run {
            adapter = cardAdapter
            //Fixme MarginDecoration 고쳐야겠다
            //Todo: 바깥쪽 여백도 줄 수 있도록 하기
            //addItemDecoration(MarginDecoration(this@RoundListActivity, 2, 20, 20))
        }

        cardAdapter.addAll(loadCardListOfRound())
    }

    //Dummy
    private fun loadCardListOfRound(): MutableList<CardModel> {
        val data = mutableListOf<CardModel>()

        data.apply {
            add(CardModel("", false, null, null))
            add(CardModel("", true, null, null))
            add(CardModel("", false, null, null))
            add(CardModel("", false, null, null))
            add(CardModel("", true, null, null))
            add(CardModel("", false, null, null))
            add(CardModel("", true, null, null))
            add(CardModel("", false, null, null))
            add(CardModel("", false, null, null))
        }

        return data
    }

    //Dummy
    private fun loadRoundDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(
                RoundDescriptionModel("베개와 유리병", "ROUND 1", "베개와 유리병의 공통점은?", "총 10분 소요", 1)
            )
            add(
                RoundDescriptionModel("베개와 유리병", "ROUND 2", "베개와 유리병의 차이점은?", "총 11분 소요", 2)
            )
            add(
                RoundDescriptionModel("베개와 유리병", "ROUND 3", "베개...유리병...", "총 12분 소요", 3)
            )
            return datas
        }
    }
}
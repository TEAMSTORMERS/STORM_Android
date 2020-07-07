package com.stormers.storm.projectcardlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.stormers.storm.R
import com.stormers.storm.card.CardAdapter
import com.stormers.storm.card.CardModel
import com.stormers.storm.projectcardlist.recyclerview.RoundListAdapter
import com.stormers.storm.projectcardlist.recyclerview.RoundDescriptionModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_project_cardlist.*

class RoundListActivity : AppCompatActivity() {

    lateinit var roundListAdapter: RoundListAdapter

    private val cardAdapter: CardAdapter by lazy { CardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)

        roundListAdapter = RoundListAdapter()

        viewpager_roundcardlist_round.run {
            adapter = roundListAdapter

            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            offscreenPageLimit = 3
        }

        roundListAdapter.addAll(loadRoundDatas())

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
            add(CardModel("", false))
            add(CardModel("", true))
            add(CardModel("", false))
            add(CardModel("", false))
            add(CardModel("", true))
            add(CardModel("", false))
            add(CardModel("", true))
            add(CardModel("", false))
            add(CardModel("", false))
        }

        return data
    }

    //Dummy
    private fun loadRoundDatas() : MutableList<RoundDescriptionModel> {

        val datas = mutableListOf<RoundDescriptionModel>()

        datas.apply {
            add(
                RoundDescriptionModel(
                    Textview_project_title_roundinfo = "베개와 유리병의 공통점은?"
                )
            )

            add(
                RoundDescriptionModel(
                    Textview_project_title_roundinfo = "Pillow 와 Glass 의 공통점은?"
                )
            )

            add(
                RoundDescriptionModel(
                    Textview_project_title_roundinfo = "평화와 희원이의 공통점은?"
                )
            )
        }

        return datas
    }
}
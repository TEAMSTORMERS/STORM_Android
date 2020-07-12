package com.stormers.storm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.stormers.storm.R
import com.stormers.storm.card.adapter.ExpandCardAdapter
import com.stormers.storm.card.model.CardModel
import kotlinx.android.synthetic.main.activity_round_meeting_expand.*

class RoundMeetingExpandActivity : AppCompatActivity() {

  val adapter = ExpandCardAdapter(expandCardList)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_meeting_expand)
        viewpager_fragment_card_expand.adapter = adapter


    }
    companion object{
        val expandCardList = arrayListOf(
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null),
            CardModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",true,null)

            )
    }
}
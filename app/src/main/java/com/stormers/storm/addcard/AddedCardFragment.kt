package com.stormers.storm.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.stormers.storm.R
import com.stormers.storm.addcard.recyclerview.AddedCardAdapter
import com.stormers.storm.addcard.recyclerview.AddedCardData
import kotlinx.android.synthetic.main.fragment_added_card.*

class AddedCardFragment : Fragment() {

    lateinit var addedCardAdapter: AddedCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_added_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RecyclerView_added_card.layoutManager = GridLayoutManager(context, 2)

        addedCardAdapter = AddedCardAdapter(view.context)
        RecyclerView_added_card.adapter = addedCardAdapter
        loadAddedCardDatas()
    }

    private fun loadAddedCardDatas() {

        val datas = mutableListOf<AddedCardData>()

        datas.apply {
            add(
                AddedCardData(
                    ImageView_added_card = context?.getDrawable(R.drawable.round2_card1)
                )
            )
            add(
                AddedCardData(
                    ImageView_added_card = context?.getDrawable(R.drawable.round2_card1)
                )
            )
            add(
                AddedCardData(
                    ImageView_added_card = context?.getDrawable(R.drawable.round2_card1)
                )
            )
            add(
                AddedCardData(
                    ImageView_added_card = context?.getDrawable(R.drawable.round2_card1)
                )
            )
        }

        addedCardAdapter.datas = datas
        addedCardAdapter.notifyDataSetChanged()
    }

}
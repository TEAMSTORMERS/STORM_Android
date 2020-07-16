package com.stormers.storm.card.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.card.adapter.CardAdapter
import com.stormers.storm.card.adapter.SavedCardAdapter
import com.stormers.storm.card.model.CardModel
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.ui.RoundFinishActivity
import com.stormers.storm.ui.RoundMeetingExpandActivity
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.user.UserModel
import kotlinx.android.synthetic.main.fragment_roundmeeting.*
import kotlin.math.round

class RoundmeetingFragment : BaseFragment(R.layout.fragment_roundmeeting) {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }
    lateinit var roundmeetingAdapter: SavedCardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundmeetingAdapter = SavedCardAdapter(true, object: SavedCardAdapter.OnCardClickListener {
            override fun onCardClick(projectIdx: Int, roundIdx: Int, cardId: Int) {
                val intent = Intent(context, RoundMeetingExpandActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                intent.putExtra("roundIdx", roundIdx)
                intent.putExtra("cardId", cardId)
                startActivity(intent)
            }
        })
        RecyclerView_added_card_roundmeeting.adapter = roundmeetingAdapter
        val data = preference.getProjectIdx()?.let { savedCardRepository.getAll(it, preference.getRoundIdx()!!) }
        roundmeetingAdapter.addAll(data)
    }
}
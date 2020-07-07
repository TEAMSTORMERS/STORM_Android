package com.stormers.storm.projectcardlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import kotlinx.android.synthetic.main.item_participated_projects_list.view.*

class RoundCardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_round_info_card, container, false)
    }
}
package com.stormers.storm.project.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.UserModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*

class WaitingForStartingProjectFragment : Fragment() {
    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_waiting_for_starting_project, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.include_waitingproject_participant.recyclerview_participant.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginDecoration(context, 15, RecyclerView.VERTICAL))
            adapter = participantAdapter
        }

        participantAdapter.addAll(loadData())

        cardview_waitingproject_checkrules.setOnClickListener{

            //룰 리마인더 다이얼로그
            val buttonArray = ArrayList<StormDialogButton>()

            buttonArray.add(
                StormDialogButton(
                    "확인",
                    object :
                        StormDialogButton.OnClickListener {
                        override fun onClick() {
                            //Toast.makeText(context, "확인 눌렀음", Toast.LENGTH_SHORT).show()
                            imageview_checkrules_uncheckcircle.visibility = View.INVISIBLE
                            imageview_checkrules_checkcircle.visibility = View.VISIBLE
                        }
                    })
            )

            getFragmentManager()?.let { it1 ->
                StormDialogBuilder(
                    R.drawable.m_brainstormingreminder_popup_lightning,
                    "브레인스토밍 룰 리마인더"
                )
                    .setContentRes(R.layout.view_rule_reminder)
                    .setButtonArray(buttonArray)
                    .build()
                    .show(it1, StormDialog.TAG)
            }

        }



    }



    private fun loadData(): MutableList<UserModel> {
        val data = mutableListOf<UserModel>()

        //Dummy data
        data.add(
            UserModel(
                "https://www.notion.so/STORM-e0234061dd594af79f1035691830e698#8f611dc7d34b42f785d65cf7cc7a95bb",
                "김성규"
            )
        )
        data.add(
            UserModel(
                "https://www.notion.so/STORM-e0234061dd594af79f1035691830e698#0a957fd1e94d43739b018f87d3cadd2b",
                "손평화"
            )
        )
        data.add(
            UserModel(
                "https://www.notion.so/STORM-e0234061dd594af79f1035691830e698#56815b6b35c347109dc3bd3434bd6041",
                "강희원"
            )
        )

        return data
    }
}
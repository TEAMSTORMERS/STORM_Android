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
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.UserModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_setting_waiting_member.*
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

        //stormButton_start_host_round_setting이 비활성화 상태에서 활성 상태로 잘 바뀌는지 테스트하기 위한 코드입니다
        //val stormbutton: StormButton? = activity?.findViewById(R.id.stormButton_start_host_round_setting)

        cardview_waitingproject_checkrules.setOnClickListener {
            val buttonArray = ArrayList<StormDialogButton>()

            buttonArray.add(
                StormDialogButton("확인", false, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        //Toast.makeText(context, "확인 눌렀음", Toast.LENGTH_SHORT).show()
                        imageview_checkrules_uncheckcircle.visibility = View.INVISIBLE
                        imageview_checkrules_checkcircle.visibility = View.VISIBLE

                        //stormButton_start_host_round_setting이 비활성화 상태에서 활성 상태로 잘 바뀌는지 테스트하기 위한 코드입니다
                        /*if (stormbutton != null) {
                            stormbutton.setActivation(true)
                        }*/
                    }
                })
            )

            getFragmentManager()?.let { it1 ->
                StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "브레인스토밍 룰 리마인더")
                    .setContentRes(R.layout.view_rule_reminder)
                    .setHorizontalArray(buttonArray)
                    .build()
                    .show(it1, "rulereminder")
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
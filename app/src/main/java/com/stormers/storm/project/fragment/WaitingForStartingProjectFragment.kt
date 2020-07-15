package com.stormers.storm.project.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.InterfaceProjectInfo
import com.stormers.storm.network.InterfaceProjectUser
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.model.ResponseProjectInfoModel
import com.stormers.storm.project.model.ResponseProjectUserListModel
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import com.stormers.storm.ui.HostRoundWaitingActivity
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.UserModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingForStartingProjectFragment : BaseFragment(R.layout.fragment_waiting_for_starting_project) {

    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    private lateinit var activityButton: StormButton

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    private var projectIdx = -1

    private lateinit var retrofitClient: InterfaceProjectUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        showProjectUserList()

        projectIdx = (activity as HostRoundWaitingActivity).projectIdx

        activityButton = (activity as HostRoundWaitingActivity).stormButton_ok_host_round_setting

        activityButton.run {
            setText("시작")
            setActivation(false)

            setOnClickListener {
                goToFragment(HostRoundSettingFragment::class.java, null)
            }
        }

        view.include_waitingproject_participant.recyclerview_participant.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginDecoration(context, 15, RecyclerView.VERTICAL))
            adapter = participantAdapter
        }


        buttonArray.add(
            StormDialogButton("확인", false, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    imageview_waitingproject_checkcircle.setImageResource(R.drawable.h_projectwaiting_brainstorming_ok_selected)

                    activityButton.setActivation(true)
                }
            })
        )

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "브레인스토밍 룰 리마인더")
                .setContentRes(R.layout.view_rule_reminder)
                .setHorizontalArray(buttonArray)
                .build()

        cardview_waitingproject_checkrules.setOnClickListener {
            fragmentManager?.let { it1 -> dialog.show(it1, "rule_reminder") }
        }
    }

    private fun showProjectUserList() {

        retrofitClient = RetrofitClient.create(InterfaceProjectUser::class.java)

        retrofitClient.getProjectUserList(projectIdx).enqueue(object : Callback<ResponseProjectUserListModel>{
            override fun onFailure(call: Call<ResponseProjectUserListModel>, t: Throwable) {
                Log.d("projectUser 통신실패","${t}")
            }

            override fun onResponse(
                call: Call<ResponseProjectUserListModel>,
                response: Response<ResponseProjectUserListModel>
            ) {
                if(response.isSuccessful)
                    if(response.body()!!.success){
                        Log.d("user list 통신성공","성공")
                        participantAdapter.addAll(response.body()!!.data)
                    }

            }
        })
    }
}
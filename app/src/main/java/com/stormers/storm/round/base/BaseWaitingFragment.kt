package com.stormers.storm.round.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.network.InterfaceProjectUser
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.project.model.ResponseProjectUserListModel
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseWaitingFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    private lateinit var retrofitClient: InterfaceProjectUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.include_waitingproject_participant.recyclerview_participant.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginDecoration(context, 15, RecyclerView.VERTICAL))
            adapter = participantAdapter
        }

        showProjectUserList()
    }

    private fun showProjectUserList() {

        retrofitClient = RetrofitClient.create(InterfaceProjectUser::class.java)

        preference.getProjectIdx()?.let {
            retrofitClient.getProjectUserList(it).enqueue(object :
                Callback<ResponseProjectUserListModel> {
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
}
package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.project.model.ResponseProjectUserListModel
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.network.InterfaceRoundUser
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.UserModel
import com.stormers.storm.util.MarginDecoration
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_round_setting.*
import kotlinx.android.synthetic.main.layout_list_of_participant.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberWaitingFragment : BaseWaitingFragment(R.layout.fragment_round_setting_waiting_member) {

    private lateinit var activityButton: StormButton
    private lateinit var retrofitClient : InterfaceRoundUser

    private lateinit var recentRoundUserAdapter: ParticipantAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityButton = (activity as BaseProjectWaitingActivity).stormButton_ok_host_round_setting

        setRecyclerView(view)

        activityButton.run {
            visibility = View.GONE

            getUserList()

        }

        //Todo: 소켓통신으로 호스트가 준비 완료하면 라운드 시작해야함 ~~

    }

    fun setRecyclerView(view : View){
        recentRoundUserAdapter = ParticipantAdapter()
        recentRoundUserAdapter.add(UserModel("test","test"))

        view.recycler_participated_projects_list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        view.recycler_participated_projects_list.adapter = recentRoundUserAdapter
    }

    fun showUserList(){
        retrofitClient = RetrofitClient.create(InterfaceRoundUser::class.java)

        retrofitClient.showRoundUser(1).enqueue(object : Callback<ResponseProjectUserListModel>{
            override fun onFailure(call: Call<ResponseProjectUserListModel>, t: Throwable) {
                Log.d("유저정보 통신실패", "${t}")
            }

            override fun onResponse(
                call: Call<ResponseProjectUserListModel>,
                response: Response<ResponseProjectUserListModel>
            ) {
                if(response.isSuccessful){
                    if (response.body()!!.success){

                        val data = response.body()!!.data
                        Log.e("data",response.body()!!.toString())

                        //Todo:리사이클러뷰 연결해서 뿌려주어야함

                        recentRoundUserAdapter.addAll(data)
                    }
                }


            }

        })

    }

    fun getUserList(){

        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("joinRoom", "roomCode")
        SocketClient.sendEvent("roundSetting",  "roomCode")

        SocketClient.responseEvent("roundcomplete", Emitter.Listener {
            Log.d("소켓 성공", it.toString())

            showUserList()
        })
    }


}
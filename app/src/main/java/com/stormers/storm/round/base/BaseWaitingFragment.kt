package com.stormers.storm.round.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.network.response.ResponseProjectUserListModel
import com.stormers.storm.round.network.response.ResponseRoundInfoModel
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.fragment_round_setting_waiting_member.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

abstract class BaseWaitingFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var dialog: StormDialog

    private lateinit var retrofitClient: RequestProject

    private lateinit var retrofitList: com.stormers.storm.round.network.RequestRound

    private lateinit var roundTime : TextView

    private lateinit var roundSubject : TextView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundTime = view.findViewById(R.id.round_time)
        roundSubject = view.findViewById(R.id.round_subject)



        view.include_waitingproject_participant.recyclerview_participant.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginDecoration(context, 15, RecyclerView.VERTICAL))
            adapter = participantAdapter
        }

        view.cardview_roundwaiting_rulereminder.setOnClickListener {
            dialog.show(fragmentManager!!, "rule_reminder")
        }

        buttonArray.add(
            StormDialogButton("확인", false, null)
        )

        dialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "브레인스토밍 룰 리마인더")
            .setContentRes(R.layout.view_rule_reminder)
            .setHorizontalArray(buttonArray)
            .build()
    }

    //안쓸 것 같음
    private fun showProjectUserList() {

        retrofitClient = RetrofitClient.create(RequestProject::class.java)

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

    protected fun showRoundUserLIst(roundIdx: Int) {

        RetrofitClient.create(RequestRound::class.java).showRoundUser(roundIdx)
            .enqueue(object : Callback<ResponseProjectUserListModel> {

            override fun onFailure(call: Call<ResponseProjectUserListModel>, t: Throwable) {
                Log.d("라운드 유저 리스트 ", "${t}")
            }
            override fun onResponse(call: Call<ResponseProjectUserListModel>, response: Response<ResponseProjectUserListModel>) {
                participantAdapter.clear()
                participantAdapter.addAll(response.body()!!.data)
            }
        })
    }

    protected fun getRoundInfo(){

        RetrofitClient.create(RequestRound::class.java).responseRoundInfo(preference.getProjectIdx()!!).enqueue(object : Callback<ResponseRoundInfoModel>{
            override fun onFailure(call: Call<ResponseRoundInfoModel>, t: Throwable) {
                Log.d("RoundInfo 통신실패", "{$t}")
            }
            override fun onResponse(call: Call<ResponseRoundInfoModel>, response: Response<ResponseRoundInfoModel>) {

                if(response.isSuccessful){

                    if(response.body()!!.success){
                        Log.d("RoundInfo 통신성공","성공")

                        val time = StringBuilder()
                        time.append("총 ")
                            .append(response.body()!!.data.roundTime)
                            .append("분 예정")

                        roundTime.text = time.toString()
                        roundSubject.text = response.body()!!.data.roundPurpose

                        preference.setRoundIdx(response.body()!!.data.roundIdx)

                        afterGettingRoundInfo(response.body()!!.data.roundIdx)
                    }
                }
            }
        })
    }

    abstract fun afterGettingRoundInfo(roundIdx: Int)
}
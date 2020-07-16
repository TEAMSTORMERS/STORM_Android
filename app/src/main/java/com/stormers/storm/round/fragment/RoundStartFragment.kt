package com.stormers.storm.round.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.UserModel
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.customview.StormButton
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.network.InterfaceRoundInfo
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.model.ResponseRoundInfoModel
import com.stormers.storm.ui.HostRoundWaitingActivity
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.ui.RoundSettingActivity
import com.stormers.storm.ui.RoundStartActivity
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.*
import kotlinx.android.synthetic.main.fragment_round_start.view.*
import kotlinx.android.synthetic.main.fragment_round_start.view.round_subject
import kotlinx.android.synthetic.main.fragment_round_start.view.textview_round_no
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RoundStartFragment : BaseWaitingFragment(R.layout.fragment_round_start) {

    private lateinit var activityButton: StormButton

    private lateinit var dialog: StormDialog

<<<<<<< HEAD
    private var isNewRound = false
=======
    private var projectIdx = -1

    private lateinit var retrofitClient: InterfaceRoundInfo

>>>>>>> feature/round_info_backend

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

<<<<<<< HEAD
        isNewRound = arguments?.getBoolean("newRound")?: false
=======
        projectIdx = (activity as HostRoundWaitingActivity).projectIdx
>>>>>>> feature/round_info_backend

        dialog = StormDialogBuilder(StormDialogBuilder.LOADING_LOGO, "5초 후 라운드가 시작합니다").build()

        initActivityButton()

        getRoundInfo()
    }

    private fun initActivityButton() {

        activityButton = if (isNewRound) {
            (activity as RoundSettingActivity).stormButton_ok_host_round_setting
        } else {
            (activity as HostRoundWaitingActivity).stormButton_ok_host_round_setting
        }

        activityButton.setOnClickListener {
            fragmentManager?.let { it1 -> dialog.show(it1, "round_start") }

            val handler = Handler()
            val handlerTask = Runnable {
                startActivity(Intent(activity, RoundProgressActivity::class.java))
            }

            handler.postDelayed(handlerTask, 5000)
        }
    }

    fun getRoundInfo(){
        retrofitClient = RetrofitClient.create(InterfaceRoundInfo::class.java)

        retrofitClient.responseRoundInfo(projectIdx).enqueue(object : Callback<ResponseRoundInfoModel>{
            override fun onFailure(call: Call<ResponseRoundInfoModel>, t: Throwable) {
                Log.d("RoundInfo 통신실패", "{$t}")
            }

            override fun onResponse(
                call: Call<ResponseRoundInfoModel>,
                response: Response<ResponseRoundInfoModel>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        Log.d("RoundInfo 통신성공","성공")
                        textview_round_no.setText("ROUND${response.body()!!.data.roundNumber}")
                        round_subject.setText(response.body()!!.data.roundPurpose)
                        round_subject.setText("총 ${response.body()!!.data.roundTime}분 예정")
                    }
                }
            }
        })
    }
}
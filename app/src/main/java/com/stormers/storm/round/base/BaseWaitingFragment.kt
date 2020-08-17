package com.stormers.storm.round.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.network.response.ResponseProjectUserListModel
import com.stormers.storm.round.RoundRepository
import com.stormers.storm.round.model.RoundEntity
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.UserModel
import com.stormers.storm.user.UserRepository
import com.stormers.storm.util.MarginDecoration
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_round_setting_waiting_member.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

abstract class BaseWaitingFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    companion object {
        private const val START_DELAY = 5000L
    }

    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    private val roundRepository: RoundRepository by lazy { RoundRepository.getInstance() }

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var ruleReminderDialog: StormDialog

    private lateinit var loadingDialog: StormDialog

    private lateinit var roundTimeTextView : TextView

    private lateinit var roundPurposeTextView : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰 초기화
        initView(view)

        //참가자가 들어오면 갱신
        registerParticipantsSocket()

        loadingDialog = StormDialogBuilder(StormDialogBuilder.LOADING_LOGO, "5초 후 라운드가 시작합니다").build()

        //참가자 리사이클러뷰 초기화
        view.include_waitingproject_participant.recyclerview_participant.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginDecoration(context, 15, RecyclerView.VERTICAL))
            adapter = participantAdapter
        }

        //룰 리마인더 초기화
        initRuleReminder(view)
    }

    private fun initView(view: View) {
        roundPurposeTextView = view.findViewById(R.id.round_purpose)
        roundTimeTextView = view.findViewById(R.id.round_time)
    }

    private fun initRuleReminder(view: View) {
        //롤 리마인더 버튼 초기화
        view.cardview_roundwaiting_rulereminder.setOnClickListener {
            ruleReminderDialog.show(fragmentManager!!, "rule_reminder")
        }

        //룰 리마인더 다이얼로그 버튼 초기화
        buttonArray.add(
            StormDialogButton("확인", false, null)
        )

        //률 리마인더 다이얼로그 초기화
        ruleReminderDialog = StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "브레인스토밍 룰 리마인더")
            .setContentRes(R.layout.view_rule_reminder)
            .setHorizontalArray(buttonArray)
            .build()
    }

    private fun registerParticipantsSocket() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.responseEvent("roundComplete", Emitter.Listener {
            Log.d("refresh_socket", "참가자가 들어왔습니다.")
            refreshParticipants(GlobalApplication.currentRound!!.roundIdx)
        })
    }

    private fun refreshParticipants(roundIdx: Int) {
        getParticipants(roundIdx, object: UserRepository.LoadUsersCallback {
            override fun onUsersLoaded(users: List<UserModel>) {
                participantAdapter.setList(users)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "Wrong participant")
            }
        })
    }

    private fun getParticipants(roundIdx: Int, callback: UserRepository.LoadUsersCallback) {
        RetrofitClient.create(RequestRound::class.java).showRoundUser(roundIdx)
            .enqueue(object : Callback<ResponseProjectUserListModel> {

                override fun onFailure(call: Call<ResponseProjectUserListModel>, t: Throwable) {
                    Log.d(TAG, "getParticipants : fail, ${t.message}")
                    callback.onDataNotAvailable()
                }
                override fun onResponse(call: Call<ResponseProjectUserListModel>, response: Response<ResponseProjectUserListModel>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val result = response.body()!!.data
                            Log.d(TAG, "getParticipants: Success, $result")

                            if (result.isEmpty()) {
                                callback.onDataNotAvailable()
                            } else {
                                callback.onUsersLoaded(result)
                            }
                        } else {
                            callback.onDataNotAvailable()
                        }
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
            })
    }

    protected fun initRoundInfo(roundPurpose: String, roundTime: Int) {
        val time = StringBuilder()
        time.append("총 ")
            .append(roundTime)
            .append("분 예정")

        roundTimeTextView.text = time.toString()
        roundPurposeTextView.text = roundPurpose
    }

    protected fun startRound() {
        val handler = Handler(Looper.getMainLooper())
        val handlerTask = Runnable {

            getParticipants(GlobalApplication.currentRound!!.roundIdx, object : UserRepository.LoadUsersCallback {
                override fun onUsersLoaded(users: List<UserModel>) {
                    onStartRound(users)

                    startActivity(Intent(activity, RoundProgressActivity::class.java))
                    activity?.finish()
                }

                override fun onDataNotAvailable() {
                    Log.e(TAG, "Wrong participants")
                }
            })
        }

        handler.postDelayed(handlerTask, START_DELAY)

        fragmentManager?.let {
            loadingDialog.show(it, "round_start")
        }
    }

    private fun onStartRound(participants: List<UserModel>) {
        //저장해둔 현재 라운드 정보를 DB에 저장
        GlobalApplication.run {
            currentRound!!.participants = participants
            roundRepository.insert(currentProject!!.projectIdx, currentRound!!)
        }
    }
}
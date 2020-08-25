package com.stormers.storm.round.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
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
import com.stormers.storm.project.data.source.ProjectRepository
import com.stormers.storm.project.network.response.ResponseProjectUserListModel
import com.stormers.storm.round.RoundRepository
import com.stormers.storm.round.network.RequestRound
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.user.ParticipantAdapter
import com.stormers.storm.user.User
import com.stormers.storm.user.UserRepository
import com.stormers.storm.util.MarginDecoration
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_memberwaiting.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseWaitingFragment(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {

    companion object {
        private const val START_DELAY = 5000L
    }

    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    private val roundRepository: RoundRepository by lazy { RoundRepository.getInstance() }

    private val projectRepository: ProjectRepository by lazy { ProjectRepository.getInstance() }

    private val buttonArray = ArrayList<StormDialogButton>()

    private lateinit var ruleReminderDialog: StormDialog

    private lateinit var loadingDialog: StormDialog

    protected var isFirstRound = true

    private var mActivity: Activity? = null

    private var cacheParticipants: List<User>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog()

        isFirstRound = arguments?.getBoolean("isFirstRound") ?: true

        val isPromotion = arguments?.getBoolean("isPromotion") ?: false

        loadingDialog = StormDialogBuilder(StormDialogBuilder.LOADING_LOGO, "5초 후 라운드가 시작합니다").build()

        //참가자 리사이클러뷰 초기화
        view.include_waitingproject_participant.recyclerview_participant.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginDecoration(context, 15, RecyclerView.VERTICAL))
            adapter = participantAdapter
        }

        //룰 리마인더 초기화
        initRuleReminder(view)

        //호스트로 승격한 경우 참가자만 초기화하고 이 외 초기화는 하지 않음
        if (isPromotion) {
            refreshParticipants(GlobalApplication.currentRound!!.roundIdx)
            return
        }

        //참가자가 들어오면 갱신
        registerParticipantsSocket()

        //라운드에 입장
        if (isFirstRound) {
            joinRoom()
        } else {
            enterNextRound()
        }
    }

    private fun enterNextRound() {
        SocketClient.sendEvent(SocketClient.ENTER_NEXT_ROUND, GlobalApplication.currentProject!!.projectCode!!)
        Log.d(TAG, "[socket] enterNextRound: ${GlobalApplication.currentProject!!.projectCode!!}")
    }

    private fun joinRoom() {
        SocketClient.sendEvent(SocketClient.JOIN_ROOM, GlobalApplication.currentProject!!.projectCode!!)

        Log.d(TAG, "[socket] joinRoom: projectCode: ${GlobalApplication.currentProject!!.projectCode!!}")
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
        SocketClient.responseEvent(SocketClient.ROUND_COMPLETE, Emitter.Listener {
            Log.d(TAG, "[socket] roundComplete: 참가자가 들어왔습니다.")
            refreshParticipants(GlobalApplication.currentRound!!.roundIdx)
        })

        Log.d(TAG, "[socket] roundComplete: set")
    }

    private fun refreshParticipants(roundIdx: Int) {
        getParticipants(roundIdx, object: UserRepository.LoadUsersCallback {
            override fun onUsersLoaded(users: List<User>) {
                participantAdapter.setList(users)
                cacheParticipants = users

                //원래 호스트가 아니었고, 갱신한 참가자 목록에서 내가 호스트라면
                if (checkIsHost(users) && !GlobalApplication.isHost) {
                    //호스트가 되어라
                    beHost()
                }
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "Wrong participant")
            }
        })
    }

    private fun checkIsHost(participants: List<User>): Boolean {
        for (participant in participants) {
            if (participant.isHost == 1) {
                return participant.userIdx == GlobalApplication.userIdx
            }
        }
        return false
    }

    private fun getParticipants(roundIdx: Int, callback: UserRepository.LoadUsersCallback) {
        Log.d(TAG, "getParticipants: projectIdx: ${GlobalApplication.currentProject!!.projectIdx}, roundIdx: $roundIdx")
        RetrofitClient.create(RequestRound::class.java).showRoundUser(GlobalApplication.currentProject!!.projectIdx, roundIdx)
            .enqueue(object : Callback<ResponseProjectUserListModel> {

                override fun onFailure(call: Call<ResponseProjectUserListModel>, t: Throwable) {
                    dismissLoadingDialog()
                    Log.d(TAG, "getParticipants : fail, ${t.message}")
                    callback.onDataNotAvailable()
                }
                override fun onResponse(call: Call<ResponseProjectUserListModel>, response: Response<ResponseProjectUserListModel>) {
                    dismissLoadingDialog()
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val result = response.body()!!.data
                            Log.d(TAG, "getParticipants: Success, $result")

                            if (result.isEmpty()) {
                                callback.onDataNotAvailable()
                                Log.d(TAG, "but empty")
                            } else {
                                callback.onUsersLoaded(result)
                            }
                        } else {
                            Log.d(TAG, "getParticipants: Not success ${response.body()!!.message}")
                            callback.onDataNotAvailable()
                        }
                    } else {
                        Log.d(TAG, "getParticipants: Not success ${response.message()}")
                        callback.onDataNotAvailable()
                    }
                }
            })
    }

    protected fun startRound() {
        val handler = Handler(Looper.getMainLooper())
        val handlerTask = Runnable {
            mActivity?.startActivity(Intent(mActivity, RoundProgressActivity::class.java))
            mActivity?.finish()
        }

        onRoundStart()

        handler.postDelayed(handlerTask, START_DELAY)

        fragmentManager?.let {
            loadingDialog.show(it, "round_start")
        }
    }

    private fun saveRoundInDB(participants: List<User>) {
        //저장해둔 현재 라운드와 프로젝트의 정보를 DB에 저장
        GlobalApplication.run {
            currentRound!!.participants = participants
            roundRepository.insert(currentProject!!.projectIdx, currentRound!!)
            projectRepository.insert(currentProject!!)
        }
    }

    protected open fun onRoundStart() {
        //입장 이벤트 그만 받기
        SocketClient.offEvent(SocketClient.ROUND_COMPLETE)

        //디비에 현재 참가자 목록 저장
        cacheParticipants?.let { saveRoundInDB(it) } ?: Log.e(TAG, "Wrong participants")

        Log.d(TAG, "onRoundStart: Start round !!")
    }

    protected open fun beHost() {
        Log.d(TAG, "beHost : I'm Host")
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mActivity = null
    }
}
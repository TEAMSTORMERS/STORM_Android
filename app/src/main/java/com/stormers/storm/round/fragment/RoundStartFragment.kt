package com.stormers.storm.round.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.customview.StormButton
import com.stormers.storm.network.SocketClient
import com.stormers.storm.round.RoundRepository
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.ui.RoundSettingActivity
import com.stormers.storm.user.UserModel
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_round_setting.*


class RoundStartFragment : BaseWaitingFragment(R.layout.fragment_round_start) {

    private lateinit var activityButton: StormButton

    private var roundIdx: Int? = null

    private var currentRoundModel: RoundModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundIdx = preference.getRoundIdx()

        //라운드 정보 받아오기
        getRoundInfoFromDB()

        showRoundUserLIst(roundIdx!!)

        refreshParticipantSocket()
    }

    private fun getRoundInfoFromDB() {
        roundRepository.get(roundIdx!!, object: RoundRepository.GetRoundCallback {
            override fun onRoundLoaded(round: RoundModel) {
                currentRoundModel = round
                setRoundData(round)
            }

            override fun onDataNotAvailable() {
                Log.e(TAG, "No round data")
            }
        })
    }

    override fun afterGettingRoundInfo(roundIdx: Int) {
        initActivityButton()
    }

    override fun onStartRound(participants: List<UserModel>) {
        currentRoundModel?.let{
            //Todo: 참가자 목록 집어넣기
            it.roundParticipantsIdx = null
            roundRepository.update(it)
        }
    }

    private fun initActivityButton() {

        activityButton = (activity as RoundSettingActivity).stormButton_ok_host_round_setting

        activityButton.setOnClickListener {

            startRoundSocket()

            startRound()
        }
    }

    private fun startRoundSocket() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.sendEvent("roundStartHost", preference.getProjectCode()!!)
    }

    private fun refreshParticipantSocket() {
        SocketClient.getInstance()
        SocketClient.connection()

        SocketClient.responseEvent("roundComplete", Emitter.Listener {
            Log.d("refresh_socket", "참가자가 들어왔습니다.")
            showRoundUserLIst(preference.getRoundIdx()!!)
        })
    }

}
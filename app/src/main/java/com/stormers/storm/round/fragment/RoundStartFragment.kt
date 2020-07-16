package com.stormers.storm.round.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.stormers.storm.round.base.BaseWaitingFragment
import com.stormers.storm.ui.HostRoundWaitingActivity
import com.stormers.storm.ui.RoundProgressActivity
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_round_start.view.*
import kotlinx.android.synthetic.main.layout_list_of_participant.view.*


class RoundStartFragment : BaseWaitingFragment(R.layout.fragment_round_start) {
    private lateinit var activityButton: StormButton

    private lateinit var dialog: StormDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = StormDialogBuilder(StormDialogBuilder.LOADING_LOGO, "5초 후 라운드가 시작합니다").build()

        initActivityButton()

    }

    private fun initActivityButton() {
        activityButton = (activity as HostRoundWaitingActivity).stormButton_ok_host_round_setting

        activityButton.setOnClickListener {
            fragmentManager?.let { it1 -> dialog.show(it1, "round_start") }

            val handler = Handler()
            val handlerTask = Runnable {
                startActivity(Intent(activity, RoundProgressActivity::class.java))
            }

            handler.postDelayed(handlerTask, 5000)
        }
    }
}
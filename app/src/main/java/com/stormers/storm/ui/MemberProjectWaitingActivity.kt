package com.stormers.storm.ui

import android.os.Bundle
import android.os.PersistableBundle
import com.stormers.storm.R
import com.stormers.storm.project.base.BaseProjectWaitingActivity
import com.stormers.storm.round.fragment.MemberWaitingFragment

class MemberProjectWaitingActivity : BaseProjectWaitingActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


        //fix me: 여기에서 프래그먼트 전환하면 안먹힘 BaseProjectWaitingActivity를 해야 먹힘
    }
}
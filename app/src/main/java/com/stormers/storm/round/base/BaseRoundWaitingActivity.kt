package com.stormers.storm.round.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.activity_round_setting.*

open class BaseRoundWaitingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        stormtoolbar_roundsetting.setExitButton(View.OnClickListener {
            //Todo: 프로젝트 나가기
        })

        //상단 카드 설정
        textview_projectcard_title.text = GlobalApplication.currentProject!!.projectName

        constraintlayout_roundsetting_projectcard.setOnClickListener {
            val clipboardManager = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clipData = ClipData.newPlainText("participate_code", GlobalApplication.currentProject!!.projectCode);

            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(application, "참여코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }
}
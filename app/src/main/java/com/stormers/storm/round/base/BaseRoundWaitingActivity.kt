package com.stormers.storm.round.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.ui.GlobalApplication
import kotlinx.android.synthetic.main.activity_round_setting.*

open class BaseRoundWaitingActivity : OnProjectActivity() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_setting)

        //참여한 프로젝트에 해당 프로젝트가 추가되었으므로 이 사실을 기록
        GlobalApplication.projectPreviewIsDirty = true

        setStormToolbar(stormtoolbar_roundsetting)

        //나가기 버튼 활성화
        setExitButton()

        //상단 카드 설정
        textview_projectcard_title.text = GlobalApplication.currentProject!!.projectName

        constraintlayout_roundsetting_projectcard.setOnClickListener {
            val clipboardManager = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clipData = ClipData.newPlainText("participate_code", GlobalApplication.currentProject!!.projectCode);

            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(application, "참여코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        showExitDialog()
    }

    //나가기 다이얼로그에서 확인을 눌렀을 때
    override fun onExitDialogPositiveClick() {
        super.onExitDialogPositiveClick()

        //현재 프래그먼트가 구현해둔 onExitRound()로직을 실행
        getCurrentFragment()?.run {
            onExitRound()
        }?: Log.e(TAG, "onExitDialogPositiveClick(): current fragment is null")
    }

    override fun initFragmentId(): Int? {
        return R.id.constraint_host_round
    }
}
package com.stormers.storm.canvas.base

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.canvas.fragment.CanvasTextFragment

abstract class BaseCanvasFragment(private val mode: Int) : BaseFragment(R.layout.fragment_round_canvas) {

    companion object {
        const val DRAWING_MODE = 100
        const val TEXT_MODE = 200
    }

    private lateinit var targetModeStr: String
    lateinit var targetFragment: Class<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (mode) {
            DRAWING_MODE -> {
                targetModeStr = "글 "
                targetFragment = CanvasTextFragment::class.java
            }

            else -> {
                targetModeStr = "그림 "
                targetFragment = CanvasDrawingFragment::class.java
            }
        }
    }

    protected fun showChangeDialog() {
        val buttonArray = ArrayList<StormDialogButton>()
        buttonArray.add(StormDialogButton("취소", true, null))

        buttonArray.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
            override fun onClick() {
                goToFragment(targetFragment, null)
            }
        }))

        StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, targetModeStr + getString(R.string.ask_canvas_mode_change))
            .setContentText(getString(R.string.notice_canvas_mode_change))
            .setHorizontalArray(buttonArray)
            .build()
            .show(fragmentManager!!, "notice")
    }
}
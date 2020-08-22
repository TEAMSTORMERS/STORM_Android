package com.stormers.storm.canvas.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.stormers.storm.R
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import com.stormers.storm.canvas.fragment.CanvasTextFragment
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundProgressActivity
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_round_canvas.*

abstract class BaseCanvasFragment(private val mode: Int, @LayoutRes private val canvasLayout: Int) :
    BaseFragment(R.layout.fragment_round_canvas) {

    companion object {
        const val DRAWING_MODE = 100
        const val TEXT_MODE = 200
    }

    private lateinit var targetModeStr: String
    private lateinit var targetFragment: Class<*>

    protected val userIdx = GlobalApplication.userIdx
    protected val projectIdx = GlobalApplication.currentProject!!.projectIdx
    protected val roundIdx = GlobalApplication.currentRound!!.roundIdx

    protected var mActivity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mActivity = context as Activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LayoutInflater.from(context).inflate(canvasLayout, cardview_roundcanvas_canvas)

        (activity as RoundProgressActivity).stormtoolbar_roundprogress.setBackButton(View.OnClickListener {
            goToFragment(AddCardFragment::class.java, null)
        })

        initCanvas()

        when (mode) {
            DRAWING_MODE -> {
                targetModeStr = "글 "
                targetFragment = CanvasTextFragment::class.java

                imagebutton_change_text.alpha = 0.5f

                imagebutton_change_text.setOnClickListener {
                    showChangeDialog()
                }
            }

            else -> {
                targetModeStr = "그림 "
                targetFragment = CanvasDrawingFragment::class.java

                imagebutton_change_draw.alpha = 0.5f

                imagebutton_change_draw.setOnClickListener {
                    showChangeDialog()
                }

                group_canvas_unredo.visibility = View.INVISIBLE
            }
        }

        stormbutton_roundcanvas_apply.setOnClickListener {
            onApplied()
        }

        imagebutton_canvas_trash.setOnClickListener {
            onTrashed()
            Toast.makeText(context, "지웠습니다!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showChangeDialog() {
        val buttonArray = ArrayList<StormDialogButton>()
        buttonArray.add(StormDialogButton("취소", true, null))

        buttonArray.add(StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
            override fun onClick() {
                goToFragment(targetFragment, null)
            }
        }))

        StormDialogBuilder(
            StormDialogBuilder.THUNDER_LOGO, targetModeStr + getString(R.string.ask_canvas_mode_change))
            .setContentText(getString(R.string.notice_canvas_mode_change))
            .setHorizontalArray(buttonArray)
            .build()
            .show(fragmentManager!!, "notice")
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mActivity = null
    }

    protected abstract fun onTrashed()

    protected abstract fun onApplied()

    protected open fun initCanvas() {
        return
    }
}
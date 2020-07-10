package com.stormers.storm.draw


import android.os.Bundle
import android.view.View
import com.stormers.storm.base.BaseCanvasFragment
import kotlinx.android.synthetic.main.fragment_round_canvas.*

class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagebutton_change_text.alpha = 0.5f

        imagebutton_change_text.setOnClickListener {
            showChangeDialog()
        }
    }
}
package com.stormers.storm.canvas.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.canvas.base.BaseCanvasFragment
import kotlinx.android.synthetic.main.fragment_round_canvas.*

class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagebutton_change_text.alpha = 0.5f

        imagebutton_change_text.setOnClickListener {
            showChangeDialog()
        }

        LayoutInflater.from(context).inflate(R.layout.view_signaturepad, cardview_roundcanvas_canvas)
        
    }
}
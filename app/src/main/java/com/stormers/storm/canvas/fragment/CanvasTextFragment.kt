package com.stormers.storm.canvas.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.repository.SavedCardRepository
import kotlinx.android.synthetic.main.fragment_round_canvas.*
import kotlinx.android.synthetic.main.view_addcard_edittext.*

class CanvasTextFragment : BaseCanvasFragment(TEXT_MODE) {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagebutton_change_draw.alpha = 0.5f

        imagebutton_change_draw.setOnClickListener {
            showChangeDialog()
        }

        LayoutInflater.from(context).inflate(R.layout.view_addcard_edittext, cardview_roundcanvas_canvas)

        imagebutton_canvas_trash.setOnClickListener {
            edittext_addcard.text = null

            Toast.makeText(context, "지웠습니다!", Toast.LENGTH_SHORT).show()
        }

        stormbutton_roundcanvas_apply.setOnClickListener {

            if (!edittext_addcard.text.isNullOrBlank()) {
                val cardText = edittext_addcard.text.toString()
                //Todo: cardText 서버로 전송

                savedCardRepository.insert(SavedCardEntity(1, 1, SavedCardEntity.FALSE, SavedCardEntity.TEXT, cardText, null))

                Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                goToFragment(AddCardFragment::class.java, null)
            } else {
                Toast.makeText(context, "카드가 비어있습니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
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

class CanvasTextFragment : BaseCanvasFragment(TEXT_MODE, R.layout.view_addcard_edittext) {

    override fun onTrashed() {
        edittext_addcard.text = null
    }

    override fun onApplied() {
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
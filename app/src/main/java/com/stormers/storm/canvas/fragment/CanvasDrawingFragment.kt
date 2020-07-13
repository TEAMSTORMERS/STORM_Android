package com.stormers.storm.canvas.fragment

import android.widget.Toast
import com.github.gcacace.signaturepad.views.SignaturePad
import com.stormers.storm.R
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.view_signaturepad.*

class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE, R.layout.view_signaturepad) {

    //그림을 그렸는지 여부
    private var isDrew = false

    override fun initCanvas() {
        signaturepad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                isDrew = true
            }

            override fun onClear() {
                isDrew = false
            }

            override fun onSigned() {
                //Doing nothing. prevent error.
                signaturepad
            }
        })
    }

    override fun onTrashed() {
        signaturepad.clear()
    }

    override fun onApplied() {
        if (isDrew) {
            //Todo: 서버로 전송 signaturepad.signatureBitmap

            //방금 그린 그림을 DB에 저장
            //우선은 projectIdx = 1, roundIdx = 1으로 가정함
            savedCardRepository.insert(
                SavedCardEntity(1, 1, SavedCardEntity.FALSE, SavedCardEntity.DRAWING,
                    BitmapConverter.bitmapToString(signaturepad.signatureBitmap), null
                )
            )

            signaturepad.clear()
            Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

            goToFragment(AddCardFragment::class.java, null)
        } else {
            Toast.makeText(context, "그림을 그려주세요!", Toast.LENGTH_SHORT).show()
        }
    }
}
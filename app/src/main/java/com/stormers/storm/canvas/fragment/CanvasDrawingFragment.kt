package com.stormers.storm.canvas.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.github.gcacace.signaturepad.views.SignaturePad
import com.stormers.storm.R
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.repository.SavedCardRepository
import com.stormers.storm.card.util.BitmapConverter
import kotlinx.android.synthetic.main.fragment_round_canvas.*
import kotlinx.android.synthetic.main.view_signaturepad.*

class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE) {

    private val savedCardRepository : SavedCardRepository by lazy { SavedCardRepository(context!!) }

    //그림을 그렸는지 여부
    private var isDrew = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰 초기화
        initView()
        //그림 그릴 수 있도록 초기화
        initDrawCanvas()
    }

    private fun initView() {
        imagebutton_change_text.alpha = 0.5f

        imagebutton_change_text.setOnClickListener {
            showChangeDialog()
        }

        stormbutton_roundcanvas_apply.setOnClickListener {

            if (isDrew) {
                //Todo: 서버로 전송 signaturepad.signatureBitmap

                //방금 그린 그림을 DB에 저장
                //우선은 projectIdx = 1, roundIdx = 1으로 가정함
                savedCardRepository.insert(SavedCardEntity(1, 1, SavedCardEntity.FALSE,
                    BitmapConverter.bitmapToString(signaturepad.signatureBitmap), null))

                signaturepad.clear()
                Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

                goToFragment(AddCardFragment::class.java, null)
            } else {
                Toast.makeText(context, "그림을 그려주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDrawCanvas() {
        LayoutInflater.from(context).inflate(R.layout.view_signaturepad, cardview_roundcanvas_canvas)

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

        imagebutton_canvas_trash.setOnClickListener{
            signaturepad.clear()
            Toast.makeText(context, "지웠습니다!", Toast.LENGTH_SHORT).show()
        }
    }
}
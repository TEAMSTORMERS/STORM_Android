package com.stormers.storm.customwidget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.stormers.storm.R
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.dialog_custom.view.imageview_dialog_symbol
import kotlinx.android.synthetic.main.item_dialog_buttons.view.*

/**
 * 간단 다이얼로그 생성 클래스
 * @param dialogType : 다이얼로그의 타입
 * @param message : 다이얼로그에 들어갈 문구
 * @param listener : 확인 버튼을 눌렀을 때 실행될 리스너 (필수 아님)
 * listener 는 StromDialog.OnClickListener 인터페이스를 구현하여 사용
 *
 * @const NORMAL_WITH_ONE_BUTTON : 확인 버튼과 안내 문구 하나만 있는 다이얼로그
 * @const CODE_FOR_PARTICIPATION : 참여 코드를 보여주는 다이얼로그
 * @const WAITING_NO_BUTTON : 로딩 중일 때 띄우는 다이얼로그
 */
class StormDialog(@DrawableRes val imageRes: Int, private val title: String, @LayoutRes val contentRes: Int?,
                  private val buttonArray: ArrayList<StormDialogButton>?) : DialogFragment() {

    companion object {
        const val TAG = "storm_dialog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container)

        //이미지 적용
        view.imageview_dialog_symbol.setImageResource(imageRes)

        //문구 적용
        view.textview_dialog_title.text = title

        //ContentView 적용
        contentRes?.let {
            view.linearlayout_dialog_content.addView(inflater.inflate(contentRes, container))
        }

        //버튼 적용
        buttonArray?.let {
            for (stormDialogButton in buttonArray) {
                val button = inflater.inflate(R.layout.item_dialog_buttons, container)
                button.textview_dialog_button.text = stormDialogButton.text

                button.constraintlayout_dialog_button.setOnClickListener {
                    stormDialogButton.listener.onClick()
                    dismiss()
                }

                view.linearlayout_dialog_buttons.addView(button)
            }
        }

        //직각 모서리를 없애기 위함
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return view
    }
}
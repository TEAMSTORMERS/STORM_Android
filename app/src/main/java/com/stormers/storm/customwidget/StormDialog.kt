package com.stormers.storm.customwidget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.stormers.storm.R
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.dialog_custom.view.imageview_dialog_symbol

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
class StormDialog(private val dialogType: Int, val message: String) : DialogFragment() {

    companion object {
        const val TAG = "storm_dialog"

        const val NORMAL_WITH_ONE_BUTTON = 0
        const val CODE_FOR_PARTICIPATION = 1
        const val WAITING_NO_BUTTON = 2
    }

    var listener: OnClickListener? = null

    constructor(dialogType: Int, message: String, listener: OnClickListener) : this(dialogType, message) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container)

        when (dialogType) {
            NORMAL_WITH_ONE_BUTTON -> {
                view.group_dialog_code.visibility = View.GONE
                view.textview_dialog_title.text = message
            }

            CODE_FOR_PARTICIPATION -> {
                //Todo: 참여 코드 복사 기능 추가
                view.group_dialog_code.visibility = View.VISIBLE
            }

            else -> { // WAITING_NO_BUTTON
                //Todo: 애니메이션이 계속 진행되도록
                //Todo: 로딩이 끝나면 다이얼로그를 닫을 수 있는 메서드 추가
                view.imageview_dialog_symbol.setImageResource(R.drawable.h_roundstart_popup_symbol)
                view.group_dialog_code.visibility = View.GONE
                view.constraintLayout_dialog_button.visibility = View.GONE
                view.view_dialog_divider2.visibility = View.INVISIBLE
                view.textview_dialog_title.text = message
            }
        }

        //직각 모서리를 없애기 위함
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        view.constraintLayout_dialog_button.setOnClickListener {
            listener?.onClick()
            dismiss()
        }

        return view
    }

    interface OnClickListener {
        fun onClick()
    }
}
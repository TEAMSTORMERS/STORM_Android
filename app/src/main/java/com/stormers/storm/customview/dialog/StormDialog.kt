package com.stormers.storm.customview.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.stormers.storm.R
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.dialog_custom.view.imageview_dialog_symbol
import kotlinx.android.synthetic.main.dialog_custom.view.textview_dialog_content
import kotlinx.android.synthetic.main.item_dialog_buttons.view.*

/**
 * 커스텀 다이얼로그 생성 클래스
 * @author KIM SEONGGYU
 * @since 2020.07.04
 *
 * @param dialogType  다이얼로그의 타입
 * @param message  다이얼로그에 들어갈 문구
 *
 * @param contentRes? 다이얼로그 중간에 들어갈 레이아웃
 * @param buttonArray? 새로로 정렬 될 버튼들의 배열
 * @param horizontalButtonArray? 가로로 정렬 될 버튼들의 배열
 * listener 는 StromDialogButton.OnClickListener 인터페이스를 구현하여 사용
 */
class StormDialog(@DrawableRes val imageRes: Int, private val title: String, private val contentText: String?, @LayoutRes val contentRes: Int?,
                  private val buttonArray: ArrayList<StormDialogButton>?,
                  private val horizontalButtonArray: ArrayList<StormDialogButton>?) : DialogFragment() {

    companion object {
        const val TAG = "storm_dialog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container)

        //파라미터 검사
        contentCheck()

        //이미지 적용
        view.imageview_dialog_symbol.setImageResource(imageRes)

        //문구 적용
        view.textview_dialog_title.text = title

        //contentText 적용
        contentText?.let {
            view.textview_dialog_content.text = contentText
        }?: run {
            view.textview_dialog_content.visibility = View.GONE
        }

        //ContentView 적용
        contentRes?.let {
            view.textview_dialog_content.visibility = View.GONE
            view.linearlayout_dialog_content.addView(inflater.inflate(contentRes, container))
        }

        //세로 버튼 적용
        buttonArray?.let {
            for (stormDialogButton in buttonArray) {
                val button = inflater.inflate(R.layout.item_dialog_buttons, container)
                button.textview_dialog_button.text = stormDialogButton.text

                if (stormDialogButton.accentColor) {
                    button.constraintlayout_dialog_button.setBackgroundColor(resources.getColor(R.color.storm_yellow))
                }

                button.constraintlayout_dialog_button.setOnClickListener {
                    stormDialogButton.listener.onClick()
                    dismiss()
                }

                view.linearlayout_dialog_buttons.addView(button)
            }
        }

        //가로 버튼 적용
        horizontalButtonArray?.let {
            val linearLayout = LinearLayout(context)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            for (stormDialogButton in horizontalButtonArray) {
                val button = inflater.inflate(R.layout.item_dialog_buttons, container)
                button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                button.textview_dialog_button.text = stormDialogButton.text

                if (stormDialogButton.accentColor) {
                    button.constraintlayout_dialog_button.setBackgroundColor(resources.getColor(R.color.storm_yellow))
                }

                button.constraintlayout_dialog_button.setOnClickListener {
                    stormDialogButton.listener.onClick()
                    dismiss()
                }

                linearLayout.addView(button)

                //디자인상 divider 가 빠짐
//                if (stormDialogButton != horizontalButtonArray[horizontalButtonArray.size - 1]) {
//                    val divider = View(context)
//                    divider.layoutParams = LinearLayout.LayoutParams(MetricsUtil.convertDpToPixel(1f, context).toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
//                    divider.setBackgroundResource(R.color.brownish_grey)
//
//                    view.linearlayout_dialog_horizontalbuttons.addView(divider)
//                }
            }
            view.linearlayout_dialog_buttons.addView(linearLayout)
        }

        //직각 모서리를 없애기 위함
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //별도 타이틀 없애기
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        //다이얼로그 밖 화면 터치시 닫히지 않음
        isCancelable = false

        return view
    }

    private fun contentCheck() {
        if (contentText != null && contentRes != null) {
            throw IllegalArgumentException("ContentText cannot be used with ContentRes. Please choose one of the two.")
        }
    }
}
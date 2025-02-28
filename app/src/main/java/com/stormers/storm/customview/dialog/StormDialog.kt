package com.stormers.storm.customview.dialog

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.aigestudio.wheelpicker.WheelPicker
import com.stormers.storm.R
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.item_dialog_buttons.view.*
import kotlinx.android.synthetic.main.view_participation_code.view.*
import kotlinx.android.synthetic.main.view_timepicker.view.*

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
class StormDialog(@DrawableRes val imageRes: Int, private val title: String, private val contentText: String?,
                  @LayoutRes val contentRes: Int?, private val buttonArray: ArrayList<StormDialogButton>?,
                  private val horizontalButtonArray: ArrayList<StormDialogButton>?, private val exitButton: Boolean,
                  private val cancelable: Boolean, private val isPicker: Boolean,
                  private val isCode: Boolean, private val code: String?, private val minValue: Int?,
                  private val maxValue: Int?, private val callback: OnContentAttachedCallback?) : DialogFragment() {

    companion object {
        const val TAG = "storm_dialog"

        private const val DEFAULT_MAX_VALUE_MINUTE = 20
        private const val DEFAULT_MIN_VALUE_MINUTE = 1

        const val LOADING = -1
    }

    @SuppressLint("LongLogTag")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_custom, container)

        //파라미터 검사
        contentCheck()

        //이미지 적용

        if (imageRes != LOADING) {
            view.imageview_dialog_symbol.setImageResource(imageRes)
        } else {
            view.imageview_dialog_symbol.visibility = View.GONE
            view.lottie_dialog_loading.visibility = View.VISIBLE
        }

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
            val contentView = inflater.inflate(contentRes, view.linearlayout_dialog_content)
            callback?.onContentAttached(contentView)

            if (isPicker) {
                view.numberpicker_minute.run {
                    val data = mutableListOf<Int>()
                    for (i in 1..20) {
                        data.add(i)
                    }
                    numberpicker_minute.data = data
                    numberpicker_minute.visibleItemCount = 3
                    numberpicker_minute.itemTextColor = ContextCompat.getColor(context, R.color.storm_popup_gray)
                    numberpicker_minute.selectedItemTextColor = ContextCompat.getColor(context, R.color.storm_gray)
                }
            }

            if (isCode) {
                view.textview_dialog_code.text = code

                view.button_dialog_copy.setOnClickListener {
                    val clipboardManager = it.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

                    val clipData = ClipData.newPlainText("participate_code", code);

                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(view.context, "참여코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
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
                    stormDialogButton.listener?.onClick()
                    stormDialogButton.pickerListener?.onClick(view.numberpicker_minute.currentItemPosition + 1)
                    dismiss()
                }

                view.linearlayout_dialog_buttons.addView(button)

                if (stormDialogButton != buttonArray[buttonArray.size - 1]) {
                    val divider = View(context)
                    divider.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MetricsUtil.convertDpToPixel(1f, context).toInt())
                    divider.setBackgroundResource(R.color.dialog_divider)

                    view.linearlayout_dialog_buttons.addView(divider)
                }
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
                    stormDialogButton.listener?.onClick()
                    stormDialogButton.pickerListener?.onClick(view.numberpicker_minute.currentItemPosition + 1)
                    dismiss()
                }

                linearLayout.addView(button)

                //divider
                if (stormDialogButton != horizontalButtonArray[horizontalButtonArray.size - 1]) {
                    val divider = View(context)
                    divider.layoutParams = LinearLayout.LayoutParams(MetricsUtil.convertDpToPixel(1f, context).toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
                    divider.setBackgroundResource(R.color.dialog_divider)

                    linearLayout.addView(divider)
                }
            }
            view.linearlayout_dialog_buttons.addView(linearLayout)
        }

        if (buttonArray == null && horizontalButtonArray == null) {
            ((view.linearlayout_dialog_buttons.layoutParams) as ViewGroup.MarginLayoutParams)
                .setMargins(0, MetricsUtil.convertDpToPixel(20f, context).toInt(), 0, 0)
        }

        if (exitButton) {
            view.imagebutton_dialog_exit.run {
                visibility = View.VISIBLE
                setOnClickListener {
                    dismiss()
                }
            }
        }

        //직각 모서리를 없애기 위함
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //별도 타이틀 없애기
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        //다이얼로그 밖 화면 터치시 닫힐지
        isCancelable = if (imageRes != LOADING) {
            cancelable
        } else {
            false
        }

        return view
    }

    private fun contentCheck() {
        if (contentText != null && contentRes != null) {
            throw IllegalArgumentException("ContentText cannot be used with ContentRes. Please choose one of the two.")
        }
    }

    interface OnContentAttachedCallback {
        fun onContentAttached(view: View)
    }

}
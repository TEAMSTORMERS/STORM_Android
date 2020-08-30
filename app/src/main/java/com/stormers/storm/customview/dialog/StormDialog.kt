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
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.stormers.storm.R
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.dialog_custom.view.imageview_dialog_symbol
import kotlinx.android.synthetic.main.dialog_custom.view.textview_dialog_content
import kotlinx.android.synthetic.main.item_dialog_buttons.view.*
import kotlinx.android.synthetic.main.view_participation_code.view.*
import kotlinx.android.synthetic.main.view_timepicker.*
import kotlinx.android.synthetic.main.view_timepicker.view.numberpicker_minute
import java.lang.reflect.Field

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
                  private val horizontalButtonArray: ArrayList<StormDialogButton>?, private val isPicker: Boolean,
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
                    maxValue = this@StormDialog.maxValue?: DEFAULT_MAX_VALUE_MINUTE
                    minValue = this@StormDialog.minValue?: DEFAULT_MIN_VALUE_MINUTE
                    wrapSelectorWheel = false
                    descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

                    //Todo: 테스트용으로 넣어본 색상입니다!
                    setNumberPickerTextColor(numberpicker_minute, Color.argb(255, 255, 0, 0))


//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        textColor = context.getColor(R.color.storm_gray)
//                    }

//                    (numberpicker_minute.getChildAt(10) as EditText).setTextColor(ContextCompat.getColor(context, R.color.storm_gray))
//                    numberpicker_minute.invalidate()

//                    val count = view.numberpicker_minute.childCount
//                    for (i in 0..count) {
//                        val t0 = view.numberpicker_minute.getChildAt(i)
//                        if (t0 is EditText) {
//                            try {
//                                val t1 = view.numberpicker_minute.javaClass.getDeclaredField("mSelectorWheelPaint")
//                                t1.isAccessible = true
//                                (t1.get(view.numberpicker_minute) as Paint).color = ContextCompat.getColor(context!!, R.color.storm_gray)
//                                (t0 as EditText).setTextColor(ContextCompat.getColor(context!!, R.color.storm_gray))
//                                view.numberpicker_minute.invalidate()
//                            }
//                            catch (e: Exception) {
//                                Log.d("numberpicker_minute","false")
//                            }
//                        }
//                    }

//                    try {
//                        val selectorWheelPaintField = NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
//                        selectorWheelPaintField.isAccessible = true
//                        (selectorWheelPaintField.get(numberpicker_minute) as Paint).setColor(ContextCompat.getColor(context!!, R.color.storm_gray))
//                    }
//                    catch (e: NoSuchFieldException) {
//                        Log.w("setNumberPickerTextColor", e)
//                    }
//                    catch (e: IllegalAccessException) {
//                        Log.w("setNumberPickerTextColor", e)
//                    }
//                    catch (e: IllegalArgumentException) {
//                        Log.w("setNumberPickerTextColor", e)
//                    }
//
//                    val count = numberpicker_minute.childCount
//                    for (i in 0..count) {
//                        val child = numberpicker_minute.getChildAt(i)
//                        if (child is EditText) {
//                            (child as EditText).setTextColor(ContextCompat.getColor(context!!, R.color.storm_gray))
//                        }
//                    }
//                    numberpicker_minute.invalidate()

//                    val count = childCount
//                    for (i in 0 until count) {
//                        val view = getChildAt(i)
//                        if (view is EditText) {
//                            try {
//                                view.setTextColor(ContextCompat.getColor(context!!, R.color.storm_gray))
//                                val selectorWheelPaint = NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
//                                selectorWheelPaint.isAccessible = true
//                                (selectorWheelPaint.get(this) as Paint).color = ContextCompat.getColor(context!!, R.color.storm_gray)
//                                invalidate()
//                            }
//                            catch (e: Exception) {
//                                Log.d("numberpicker_minute","false")
//                            }
//                        }
//                    }

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
                    stormDialogButton.pickerListener?.onClick(view.numberpicker_minute.value)
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
                    stormDialogButton.listener?.onClick()
                    stormDialogButton.pickerListener?.onClick(view.numberpicker_minute.value)
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

        if (buttonArray == null && horizontalButtonArray == null) {
            ((view.linearlayout_dialog_buttons.layoutParams) as ViewGroup.MarginLayoutParams)
                .setMargins(0, MetricsUtil.convertDpToPixel(20f, context).toInt(), 0, 0)
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

    interface OnContentAttachedCallback {
        fun onContentAttached(view: View)
    }

    private fun setNumberPickerTextColor(numberPicker: NumberPicker, color: Int){
        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val count = numberPicker.childCount
            for (i in 0..count) {
                val child = numberPicker.getChildAt(i)
                if (child is EditText) {
                    try {
                        child.setTextColor(color)
                        numberPicker.invalidate()
                        var selectorWheelPaintField = numberPicker.javaClass.getDeclaredField("mSelectorWheelPaint")
                        var accessible = selectorWheelPaintField.isAccessible
                        selectorWheelPaintField.isAccessible = true
                        (selectorWheelPaintField.get(numberPicker) as Paint).color = color
                        selectorWheelPaintField.isAccessible = accessible
                        numberPicker.invalidate()
                        var selectionDividerField = numberPicker.javaClass.getDeclaredField("mSelectionDivider")
                        accessible = selectionDividerField.isAccessible
                        selectionDividerField.isAccessible = true
                        selectionDividerField.set(numberPicker, null)
                        selectionDividerField.isAccessible = accessible
                        numberPicker.invalidate()
                    } catch (exception: Exception) {
                        Log.d("test", "exception $exception")
                    }
                }
            }
        //} else {
            //numberPicker.textColor = color
        //}
    }

    
}
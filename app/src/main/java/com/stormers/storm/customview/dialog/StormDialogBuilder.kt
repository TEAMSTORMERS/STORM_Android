package com.stormers.storm.customview.dialog

import androidx.annotation.LayoutRes
import com.stormers.storm.R

/**
 * StormDialog를 생성하기 위한 BuilderClass
 * @since 2020.07.05
 * @author KIM SEOUNGGYU
 *
 *  @param dialogImg 다이얼로그에 표시될 이미지
 * @param title 표시될 문구
 */
class StormDialogBuilder(private var dialogImg: Int, private var title: String) {

    //상수 모음
    companion object {
        const val STORM_LOGO = 0
        const val THUNDER_LOGO = 1
        const val LOADING_LOGO = 2
    }

    private var contentText: String? = null

    @LayoutRes
    private var contentRes: Int? = null

    private var buttonArray: ArrayList<StormDialogButton>? = null

    private var horizontalButton: ArrayList<StormDialogButton>? = null

    private var exitButton: Boolean = false

    private var cancelable: Boolean = true

    private var isPicker: Boolean = false

    private var isCode: Boolean = false

    private var code: String? = null

    private var minValue: Int? = null

    private var maxValue: Int? = null

    private var callback: StormDialog.OnContentAttachedCallback? = null

    fun build() : StormDialog {
        val imageRes = when(dialogImg) {

            STORM_LOGO -> R.drawable.h_roundstart_popup_symbol
            THUNDER_LOGO -> R.drawable.h_projectsetting_popup_lightning
            LOADING_LOGO -> StormDialog.LOADING //Todo: 애니메이션 로고 넣기

            else -> throw IllegalArgumentException("Unresolved dialog image")
        }

        return StormDialog(
            imageRes,
            title,
            contentText,
            contentRes,
            buttonArray,
            horizontalButton,
            exitButton,
            cancelable,
            isPicker,
            isCode,
            code,
            minValue,
            maxValue,
            callback
        )
    }

    fun setContentRes(contentRes: Int) : StormDialogBuilder {
        this.contentRes = contentRes
        return this
    }

    fun setButtonArray(stormDialogButtonArray: ArrayList<StormDialogButton>) : StormDialogBuilder {
        this.buttonArray = stormDialogButtonArray
        return this
    }

    fun setHorizontalArray(stormDialogButtonArray: ArrayList<StormDialogButton>) : StormDialogBuilder {
        this.horizontalButton = stormDialogButtonArray
        return this
    }

    fun setContentText(contentText: String) : StormDialogBuilder {
        this.contentText = contentText
        return this
    }

    fun setMinValue(minValue: Int) : StormDialogBuilder {
        this.minValue = minValue
        return this
    }

    fun setMaxValue(maxValue: Int) : StormDialogBuilder {
        this.maxValue = maxValue
        return this
    }

    fun setExitButton(exitButton: Boolean) : StormDialogBuilder {
        this.exitButton = exitButton
        return this
    }

    fun setCancelable(isCancelable: Boolean) : StormDialogBuilder {
        this.cancelable = isCancelable
        return this
    }

    fun isPicker(isPicker: Boolean) : StormDialogBuilder {
        this.isPicker = isPicker
        return this
    }

    fun isCode(isCode: Boolean, code: String) : StormDialogBuilder {
        this.isCode = isCode
        this.code = code
        return this
    }

    fun setOnContentAttachedCallback(callback: StormDialog.OnContentAttachedCallback) : StormDialogBuilder {
        this.callback = callback
        return this
    }
}
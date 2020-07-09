package com.stormers.storm.customview.dialog

import androidx.annotation.DrawableRes
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

    fun build() : StormDialog {
        val imageRes = when(dialogImg) {

            STORM_LOGO -> R.drawable.h_roundstart_popup_symbol
            THUNDER_LOGO -> R.drawable.h_projectsetting_popup_lightning
            LOADING_LOGO -> -1 //Todo: 애니메이션 로고 넣기

            else -> throw IllegalArgumentException("Unresolved dialog image")
        }

        return StormDialog(
            imageRes,
            title,
            contentText,
            contentRes,
            buttonArray,
            horizontalButton
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
}
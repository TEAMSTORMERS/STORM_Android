package com.stormers.storm.customwidget

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.stormers.storm.R

class StormDialogBuilder {

    companion object {
        const val CUSTOMIZING = 0
        const val CODE_FOR_PARTICIPATION = 1
    }

    private var dialogType: Int

    @DrawableRes
    private var imageRes: Int

    private var title: String

    @LayoutRes
    private var contentRes: Int? = null

    private var buttonArray: ArrayList<StormDialogButton>? = null

    constructor(dialogType: Int) {
        this.dialogType = dialogType

        when (dialogType) {
            CODE_FOR_PARTICIPATION -> {
                imageRes = R.drawable.h_projectsetting_popup_lightning
                title = "참여 코드 생성 완료!"
                contentRes = R.layout.view_participation_code

                //Todo: 참여코드 입력 파라미터 추가
                //Todo: 참여코드 복사 로직 추가

                val buttons = ArrayList<StormDialogButton>()

                buttons.add(StormDialogButton("확인", object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        //nothing to do
                    }
                }))

                this.buttonArray = buttons
            }

            else -> {
                imageRes = -1
                title = ""
            }
        }
    }

    constructor(@DrawableRes imageRes: Int, title: String) : this(CUSTOMIZING) {
        this.imageRes = imageRes
        this.title = title
    }

    fun build() : StormDialog {
        return StormDialog(imageRes, title, contentRes, buttonArray)
    }

    fun setContentRes(contentRes: Int) : StormDialogBuilder {
        this.contentRes = contentRes
        return this
    }

    fun setButtonArray(stormDialogButtonArray: ArrayList<StormDialogButton>) : StormDialogBuilder {
        this.buttonArray = stormDialogButtonArray
        return this
    }
}
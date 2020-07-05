package com.stormers.storm.customwidget.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.stormers.storm.R


/**
 * StormDialog를 생성하기 위한 BuilderClass
 * @since 2020.07.05
 * @author KIM SEOUNGGYU
 */
class StormDialogBuilder {

    //상수 모음
    //자주 만든다 싶은 다이얼로그는 여기에 상수로 등록하고 자동 생성 로직 추가
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

    private var horizontalButton: ArrayList<StormDialogButton>? = null

    //빌드 타입이 정의된 경우 자동 생성
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

                buttons.add(
                    StormDialogButton(
                        "확인",
                        object :
                            StormDialogButton.OnClickListener {
                            override fun onClick() {
                                //nothing to do
                            }
                        })
                )

                this.buttonArray = buttons
            }

            else -> {
                imageRes = -1
                title = ""
            }
        }
    }

    /**
     * 커스텀 모드 필수 파라미터
     * @param imageRes 다이얼로그에 표시될 이미지
     * @param title 표시될 문구
     */
    constructor(@DrawableRes imageRes: Int, title: String) : this(CUSTOMIZING) {
        this.imageRes = imageRes
        this.title = title
    }

    fun build() : StormDialog {
        return StormDialog(
            imageRes,
            title,
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
}
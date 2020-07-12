package com.stormers.storm.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.stormers.storm.R
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.view_card_custom.view.*


class StormCard : CardView {
    companion object {
        private const val layoutRes = R.layout.view_card_custom
        private val styleableRes = R.styleable.StormCard

        private const val RADIUS = 15f
        private const val CLICK_DELAY = 250L
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        if (attrs != null) {
            getAttrs(attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
        if (attrs != null) {
            getAttrs(attrs, defStyleAttr)
        }
    }

    private var doubleClickFlag = 0

    var heartState = false
        set(value) {
            field = value
            if (field) {
                imagebutton_customcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
            } else {
                imagebutton_customcard_heart.setImageResource(R.drawable.scrapview_heart)
            }
        }

    private var cardId = -1

    private var listener : OnHeartStateChangedListener? = null

    fun getCardId() = cardId

    fun setCardId(id: Int) {
        cardId = id
    }

    fun setOnHeartStateChangedListener(listener: StormCard.OnHeartStateChangedListener) {
        this.listener = listener
    }

    private fun init() {
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, styleableRes)

        setTypedArray(typedArray)

        typedArray.recycle()
    }

    private fun getAttrs(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, styleableRes, defStyleAttr, 0) as TypedArray

        setTypedArray(typedArray)

        typedArray.recycle()
    }

    private fun setTypedArray(typedArray: TypedArray) {
        initHeartButton(typedArray)
        
        setDoubleTab(typedArray)

        setElevation(typedArray)

        setRadius()
    }

    private fun setRadius() {
        radius = MetricsUtil.convertDpToPixel(RADIUS, context)
        cardview_customcard_root.setCardBackgroundColor(context.getColor(R.color.storm_white))
    }

    private fun setElevation(typedArray: TypedArray) {
        val elevation = typedArray.getDimension(R.styleable.StormCard_android_elevation, 1f)
        this.elevation = elevation
        useCompatPadding = true
    }

    private fun setDoubleTab(typedArray: TypedArray) {
        val isTouchable = typedArray.getBoolean(R.styleable.StormCard_isTouchable, false)

        doubleTab(isTouchable)
    }

    private fun initHeartButton(typedArray: TypedArray) {
        val isShow = typedArray.getBoolean(R.styleable.StormCard_showHeartButton, false)

        showHeartButton(isShow)
    }

    private fun switchHeartState() {
        heartState = if (!heartState) {
            imagebutton_customcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
            true
        } else {
            imagebutton_customcard_heart.setImageResource(R.drawable.scrapview_heart)
            false
        }

        //리스너 동작
        listener?.onHeartStateChanged(heartState)
    }

    fun showHeartButton(isShow: Boolean) {
        if (!isShow) {
            imagebutton_customcard_heart.visibility = View.GONE
        } else {
            imagebutton_customcard_heart.visibility = View.VISIBLE
            imagebutton_customcard_heart.setOnClickListener {
                switchHeartState()
            }
        }
    }

    fun doubleTab(isTouchable: Boolean) {
        if (isTouchable) {
            this.setOnClickListener {
                doubleClickFlag ++

                val handler = Handler()

                val clickRunnable = Runnable {
                    doubleClickFlag = 0
                }

                if (doubleClickFlag == 1) {

                    handler.postDelayed(clickRunnable, CLICK_DELAY)
                } else if (doubleClickFlag == 2) {

                    // Todo: 이벤트 효과 적용
                    doubleClickFlag = 0
                    switchHeartState()
                }
            }
        }
    }

    fun setImageUrl(url: String) {
        Glide.with(context).load(url).into(this.imageview_customcard_background)
    }

    fun setBitmap(bitmap: Bitmap) {
        imageview_customcard_background.setImageBitmap(bitmap)
    }

    fun setText(text: String) {
        textview_customcard_background.text = text
    }

    interface OnHeartStateChangedListener {
        fun onHeartStateChanged(state: Boolean)
    }
}
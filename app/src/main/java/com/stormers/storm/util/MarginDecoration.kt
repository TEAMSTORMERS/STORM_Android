package com.stormers.storm.util

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MarginDecoration
private constructor(private var context: Context) : RecyclerView.ItemDecoration() {

    private val Int.px: Int
        get() = MetricsUtil.convertDpToPixel(this.toFloat(), context).toInt()

    private var margin: Int? = null
    private var orientation: Int? = null
    private var numOfColumns: Int? = null
    private var marginHorizontal: Int? = null
    private var marginVertical: Int? = null

    /**
     * 가로나 세로 리사이클러뷰에 사용
     * 각 아이템 사이 간격을 조절
     * @param margin 간격 dp
     * @param orientation Recyclerview.Vertical, RecyclerView.Horizontal 로 사용 가능
     */
    constructor(context: Context, margin: Int, orientation: Int) : this(context) {
        this.orientation = orientation
        this.margin = margin.px
    }

    /**
     * 그리드 형태의 리사이클러뷰에 사용
     * @param numOfColumns 그리드의 가로 아이템 개수
     * @param marginHorizontal 가로 간격 dp
     * @param marginVertical 세로 간격 dp
     */
    constructor(context: Context, numOfColumns: Int, marginHorizontal: Int, marginVertical: Int) : this(context) {
        this.numOfColumns = numOfColumns
        this.marginHorizontal = marginHorizontal.px
        this.marginVertical = marginVertical.px
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        orientation?.let {
            if (position != itemCount - 1) {
                when (orientation) {
                    RecyclerView.HORIZONTAL -> outRect.right = margin!!
                    RecyclerView.VERTICAL -> outRect.bottom = margin!!
                }
            }
        }

        numOfColumns?.let {
            if (position + 1 < numOfColumns!! || (position + 1) % numOfColumns!! != 0) {
                outRect.right = marginHorizontal!! / 2
                Log.d("test", outRect.right.toString())
            }

            if (position < numOfColumns!! || position < itemCount - (numOfColumns!! - itemCount % numOfColumns!!)) {
                outRect.bottom = marginVertical!!
            }
        }
    }
}

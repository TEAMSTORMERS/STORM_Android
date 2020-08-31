package com.stormers.storm.canvas.fragment

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.rm.freedrawview.FreeDrawView
import com.rm.freedrawview.PathRedoUndoCountChangeListener
import com.stormers.storm.R
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.canvas.network.RequestCard
import com.stormers.storm.card.model.CacheCardModel
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.network.Response
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.RoundProgressActivity
import kotlinx.android.synthetic.main.fragment_round_canvas.*
import kotlinx.android.synthetic.main.view_draw.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback


class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE, R.layout.view_draw) {

    //그림을 그렸는지 여부
    private var isDrew = false

    override fun initCanvas() {
        drawview.setPathRedoUndoCountChangeListener(object : PathRedoUndoCountChangeListener {
            override fun onRedoCountChanged(redoCount: Int) {
                if (redoCount == 0) {
                    setEnableRedoButton(false)
                } else {
                    setEnableRedoButton(true)
                }
            }

            override fun onUndoCountChanged(undoCount: Int) {
                if (undoCount == 0) {
                    isDrew = false
                    setEnableUndoButton(false)
                } else {
                    isDrew = true
                    setEnableUndoButton(true)
                }
            }
        })

        imagebutton_canvas_undo.setOnClickListener {
            if (drawview.undoCount != 0) {
                drawview.undoLast()
            } else {
                Log.d(TAG, "nothing to undo")
            }
        }

        imagebutton_canvas_redo.setOnClickListener {
            if (drawview.redoCount != 0) {
                drawview.redoLast()
            } else {
                Log.d(TAG, "nothing to redo")
            }
        }

        //버튼 둘 다 비활성화
        setEnableRedoButton(false)
        setEnableUndoButton(false)
    }

    override fun onTrashed() {
        drawview.clearDrawAndHistory()
    }

    override fun onApplied() {
        if (isDrew && drawview.undoCount != 0) {

            drawview.getDrawScreenshot(object : FreeDrawView.DrawCreatorListener {
                override fun onDrawCreated(draw: Bitmap?) {
                    if (draw != null) {
                        sendBitmap(draw)
                    } else {
                        Log.d(TAG, "bitmap is null")
                    }
                }

                override fun onDrawCreationError() {
                    Log.e(TAG, "bitmap convert is fail")
                }
            })

        } else {
            Toast.makeText(context, "카드를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendBitmap(bitmap: Bitmap) {
        showLoadingDialog()

        val drawingFile = BitmapConverter.bitmapToFile(bitmap, context!!.cacheDir.toString())

        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), drawingFile!!)

        val uploadFile = MultipartBody.Part.createFormData("card_img", drawingFile.name, requestFile)

        val userIdx = RequestBody.create(MediaType.parse("text/plain"), userIdx.toString())

        val projectIdx = RequestBody.create(MediaType.parse("text/plain"), projectIdx.toString())

        val roundIdx = RequestBody.create(MediaType.parse("text/plain"), roundIdx.toString())

        RetrofitClient.create(RequestCard::class.java).postCard(userIdx, projectIdx, roundIdx, uploadFile, null)
            .enqueue(object: Callback<Response> {

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    dismissLoadingDialog()
                    Log.d("postCard", "${t.message}")
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    dismissLoadingDialog()
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {

                            saveCard(bitmap)

                            afterResponse()

                        } else {
                            Log.d("post_card", "response.body().success : false // ${response.message()}")
                        }
                    } else {
                        Log.d("post_card", "response.isSuccessful : false // ${response.message()}")
                    }
                }
            })
    }

    private fun afterResponse() {
        drawview.clearDrawAndHistory()
        Toast.makeText(context, "카드가 추가되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun saveCard(bitmap: Bitmap) {
        if (mActivity != null) {
            (mActivity as RoundProgressActivity).cardList.add(CacheCardModel(true, BitmapConverter.bitmapToString(bitmap)))
        }
    }

    private fun setEnableUndoButton(isEnable: Boolean) {
        setSaturationView(imagebutton_canvas_undo, !isEnable)
        imagebutton_canvas_undo.isClickable = isEnable
    }

    private fun setEnableRedoButton(isEnable: Boolean) {
        setSaturationView(imagebutton_canvas_redo, !isEnable)
        imagebutton_canvas_redo.isClickable = isEnable
    }

    private fun setSaturationView(view: ImageView, isSaturation: Boolean) {
        if (isSaturation) {
            view.alpha = 0.5f
        } else {
            view.alpha = 1f
        }
        Log.d(TAG, "setSaturation(): ${view.id} - $isSaturation")
    }
}
package com.stormers.storm.canvas.fragment

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.byox.drawview.enums.DrawingCapture
import com.byox.drawview.views.DrawView
import com.stormers.storm.R
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.canvas.network.RequestCard
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.network.Response
import com.stormers.storm.network.RetrofitClient
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
        drawview.setOnDrawViewListener(object : DrawView.OnDrawViewListener {
            override fun onEndDrawing() {}

            override fun onAllMovesPainted() {}

            override fun onStartDrawing() {
                isDrew = true
            }

            override fun onRequestText() {}

            override fun onClearDrawing() {
                isDrew = false
            }
        })

        imagebutton_canvas_undo.setOnClickListener {
            if (drawview.canUndo()) {
                drawview.undo()
            } else {
                Log.d(TAG, "nothing to undo")
            }
        }

        imagebutton_canvas_redo.setOnClickListener {
            if (drawview.canRedo()) {
                drawview.redo()
            } else {
                Log.d(TAG, "nothing to redo")
            }
        }
    }

    override fun onTrashed() {
        drawview.restartDrawing()
    }

    override fun onApplied() {
        if (isDrew && drawview.canUndo()) {
            val bitmap = drawview.createCapture(DrawingCapture.BITMAP)[0] as Bitmap

            val drawingFile = BitmapConverter.bitmapToFile(bitmap, context!!.cacheDir.toString())

            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), drawingFile!!)

            val uploadFile = MultipartBody.Part.createFormData("card_img", drawingFile.name, requestFile)

            val userIdx = RequestBody.create(MediaType.parse("text/plain"), preference.getUserIdx().toString())

            val projectIdx = RequestBody.create(MediaType.parse("text/plain"), preference.getProjectIdx().toString())

            val roundIdx = RequestBody.create(MediaType.parse("text/plain"), preference.getRoundIdx().toString())

            RetrofitClient.create(RequestCard::class.java).postCard(userIdx, projectIdx, roundIdx, uploadFile, null)
                .enqueue(object: Callback<Response> {

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.d("postCard", t.message)
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {

                            saveCardIntoDB(bitmap)

                            afterResponse()

                        } else {
                            Log.d("post_card", "response.body().success : false // ${response.message()}")
                        }
                    } else {
                        Log.d("post_card", "response.isSuccessful : false // ${response.message()}")
                    }
                }
            })


        } else {
            Toast.makeText(context, "카드를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun afterResponse() {
        drawview.restartDrawing()
        Toast.makeText(context, "카드가 추가되었습니다", Toast.LENGTH_SHORT).show()
        goToFragment(AddCardFragment::class.java, null)
    }

    private fun saveCardIntoDB(bitmap: Bitmap) {
        savedCardRepository.insert(
            SavedCardEntity(preference.getProjectIdx()!!, preference.getRoundIdx()!!, SavedCardEntity.FALSE, SavedCardEntity.DRAWING,
                BitmapConverter.bitmapToString(bitmap), null
            )
        )
    }
}
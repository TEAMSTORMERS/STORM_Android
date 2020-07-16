package com.stormers.storm.canvas.fragment

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.github.gcacace.signaturepad.views.SignaturePad
import com.stormers.storm.R
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.canvas.network.RequestCard
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.network.Response
import com.stormers.storm.network.RetrofitClient
import kotlinx.android.synthetic.main.view_signaturepad.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback


class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE, R.layout.view_signaturepad) {

    //그림을 그렸는지 여부
    private var isDrew = false

    override fun initCanvas() {
        signaturepad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                isDrew = true
            }

            override fun onClear() {
                isDrew = false
            }

            override fun onSigned() {
                //Doing nothing. prevent error.
                signaturepad
            }
        })
    }

    override fun onTrashed() {
        signaturepad.clear()
    }

    override fun onApplied() {
        if (isDrew) {
            val bitmap = signaturepad.signatureBitmap

            val drawingFile = BitmapConverter.bitmapToFile(bitmap, context!!.cacheDir.toString())

            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), drawingFile!!)

            val uploadFile = MultipartBody.Part.createFormData("card_img", drawingFile.name, requestFile)

            val userIdx = RequestBody.create(MediaType.parse("text/plain"), "1")

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
            Toast.makeText(context, "그림을 그려주세요!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun afterResponse() {
        signaturepad.clear()
        Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
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
package com.stormers.storm.canvas.fragment

import android.util.Log
import android.widget.Toast
import com.stormers.storm.R
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.canvas.base.BaseCanvasFragment
import com.stormers.storm.canvas.network.RequestCard
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.network.Response
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.ui.RoundProgressActivity
import kotlinx.android.synthetic.main.view_addcard_edittext.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback

class CanvasTextFragment : BaseCanvasFragment(TEXT_MODE, R.layout.view_addcard_edittext) {

    override fun onTrashed() {
        edittext_addcard.text = null
    }

    override fun onApplied() {
        val content = edittext_addcard.text.toString()
        if (!content.isNullOrBlank()) {

            val userIdx = RequestBody.create(MediaType.parse("text/plain"), GlobalApplication.userIdx.toString())

            val projectIdx = RequestBody.create(MediaType.parse("text/plain"), GlobalApplication.currentProject!!.projectIdx.toString())

            val roundIdx = RequestBody.create(MediaType.parse("text/plain"), GlobalApplication.currentRound!!.roundIdx.toString())

            val cardText = RequestBody.create(MediaType.parse("text/plain"), content)

            RetrofitClient.create(RequestCard::class.java)
                .postCard(userIdx, projectIdx, roundIdx, null, cardText).enqueue(object : Callback<Response> {

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("post_card", "onFailure : ${t.message}")
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        if (response.isSuccessful) {
                            if (response.body()!!.success) {

                                saveCard(content)

                                Toast.makeText(context, "카드가 추가되었습니다", Toast.LENGTH_SHORT).show()

                                goToFragment(AddCardFragment::class.java, null)

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

    private fun saveCard(content: String) {
        (activity as RoundProgressActivity).cardList.add(SavedCardEntity(null, null,
            null, null, null, SavedCardEntity.TEXT, content, null))
    }
}
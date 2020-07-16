package com.stormers.storm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.stormers.storm.R
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.card.network.CardInterface
import com.stormers.storm.card.network.ResponseCardData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Todo: [GET] 카드 상세보기 부분 서버 통신을 위해 새로 만든 activity 입니다.
class RoundListNetworkActivity : AppCompatActivity() {

    private lateinit var retrofitClient: CardInterface

    //private var projectIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandcard)

        val projectIdx = 2//intent.getIntExtra("projectIdx", 1)
        val roundIdx = 1//intent.getIntExtra("roundIdx", 1)
        val user_idx = 2
        val card_idx = 4
        //val cardId = intent.getIntExtra("cardId", 0)

        retrofitClient = RetrofitClient.create(CardInterface::class.java)

        retrofitClient.responseCardData(user_idx, card_idx).enqueue(object :
            Callback<ResponseCardData> {
            override fun onFailure(call: Call<ResponseCardData>, t: Throwable) {
                if (t.message != null){
                    Log.d("ExpandCardActivity", t.message!!)
                } else {
                    Log.d("ExpandCardActivity", "통신실패")
                }
            }

            override fun onResponse(
                call: Call<ResponseCardData>,
                response: Response<ResponseCardData>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("ExpandCardActivity", "카드 인덱스 : ${response.body()!!}")
                    }
                    else {
                        Log.d("ExpandCardActivity", "통신실패")
                    }
                } else {
                    Log.d("ExpandCardActivity", "${response.message()} , ${response.errorBody()}")
                }
            }

        })
    }
}
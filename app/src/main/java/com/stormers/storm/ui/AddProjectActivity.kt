package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.project.network.response.ResponseAddProject
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.model.AddProjectModel
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProjectActivity : BaseActivity() {
    private var buttonArray = ArrayList<StormDialogButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)

        preference.setProjectCode(null)

        stormtoolbar_addproject.run {
            setBackButton()
            setMyPageButton()
        }

        start_project()

        buttonArray.add(
            StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    Log.d("버튼 누름","성공")
                    startActivity(Intent(this@AddProjectActivity, RoundSettingActivity::class.java))
                    finish()
                }
            })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    fun start_project() {

        button_add_project.setOnClickListener {
            RetrofitClient.create(RequestProject::class.java)
                .addProject(AddProjectModel(edittext_addproject_projectname.text.toString(),
                    edittext_addproject_notice.text.toString(), preference.getUserIdx()!!))

                .enqueue(object : Callback<ResponseAddProject> {
                        override fun onFailure(call: Call<ResponseAddProject>, t: Throwable) {
                            Log.d("통신실패", "${t}")
                        }

                        override fun onResponse(call: Call<ResponseAddProject>, response: Response<ResponseAddProject>) {
                            if (response.isSuccessful) {
                                if (response.body()!!.success) {
                                    Log.d("AddProject", "참가 코드 : ${response.body()!!.data.projectCode}")
                                    Log.d("AddProject", "projectIdx : ${response.body()!!.data.projectIdx}")

                                    makeDialog(response.body()!!.data.projectCode)
                                        .show(supportFragmentManager, "participate_code")

                                    //프로젝트에 관한 데이터를 preference에 저장
                                    preference.setProjectIdx(response.body()!!.data.projectIdx)
                                    preference.setProjectName(edittext_addproject_projectname.text.toString())
                                    preference.setProjectCode(response.body()!!.data.projectCode)
                                }
                            } else {
                                Log.d("AddProjectActivity", response.message())
                            }
                        }
                    }
                )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun makeDialog(code: String) : StormDialog {
        return StormDialogBuilder(StormDialogBuilder.STORM_LOGO, "참여코드 생성 완료!")
            .setContentRes(R.layout.view_participation_code)
            .setButtonArray(buttonArray)
            .isCode(true, code)
            .build()
    }
}
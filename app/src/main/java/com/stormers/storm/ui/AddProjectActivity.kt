package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.model.AddProjectModel
import com.stormers.storm.project.model.ProjectModel
import com.stormers.storm.util.DateUtils
import kotlinx.android.synthetic.main.activity_add_project.*
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

        button_add_project.setOnClickListener {
            startProject()
        }

        buttonArray.add(
            StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    startActivity(Intent(this@AddProjectActivity, HostRoundSettingActivity::class.java))
                    finish()
                }
            })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun startProject() {
        val projectName = edittext_addproject_projectname.text.toString()
        val projectComment = edittext_addproject_notice.text.toString()

        RetrofitClient.create(RequestProject::class.java)
            .addProject(AddProjectModel(projectName,
                projectComment, preference.getUserIdx()!!))

            .enqueue(object : Callback<ResponseAddProject> {
                override fun onFailure(call: Call<ResponseAddProject>, t: Throwable) {
                    Log.d("통신실패", "${t}")
                }

                override fun onResponse(call: Call<ResponseAddProject>, response: Response<ResponseAddProject>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d("AddProject", "참가 코드 : ${response.body()!!.data.projectCode}")
                            Log.d("AddProject", "projectIdx : ${response.body()!!.data.projectIdx}")

                            //참여 코드 다이얼로그 띄우기
                            makeDialog(response.body()!!.data.projectCode)
                                .show(supportFragmentManager, "participate_code")

                            //현재 프로젝트에 관한 정보를 앱 전역에 저장
                            response.body()!!.data.let {
                                GlobalApplication.currentProject = ProjectModel(it.projectIdx, DateUtils.getToday(), it.projectCode,
                                    projectName, projectComment, null, null)
                            }
                            GlobalApplication.isHost = true

                            //소켓 연결 시작
                            SocketClient.getInstance()
                            SocketClient.connection()
                        }
                    } else {
                        Log.d("AddProjectActivity", response.message())
                    }
                }
            })
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
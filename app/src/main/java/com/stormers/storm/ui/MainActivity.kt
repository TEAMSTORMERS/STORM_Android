package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.project.network.response.ResponseJoinProjectUsingCode
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.*
import com.stormers.storm.project.network.response.ResponseParticipatedProject
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
    private lateinit var recentProjectsAdapter: ParticipatedProjectListAdapter
    val datas = mutableListOf<RecentProjectsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preference.setProjectCode(null)
        preference.setProjectIdx(null)
        preference.setRoundIdx(null)
        preference.setRoundCount(null)
        preference.setProjectName(null)
        preference.setHost(false)

        stormtoolbar_main.setMyPageButton()

        recentProjectsAdapter = ParticipatedProjectListAdapter(true, object : ParticipatedProjectListAdapter.OnProjectClickListener {
            override fun onProjectClick(projectIdx: Int) {
                val intent  = Intent(this@MainActivity,ParticipatedProjectDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                startActivity(intent)
            }
        })

        recycler_participated_projects_list.adapter = recentProjectsAdapter
        recycler_participated_projects_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_participated_projects_list.addItemDecoration(MarginDecoration(baseContext,16,RecyclerView.HORIZONTAL))

        loadProjectsDatas()

        moveToAddProject()

        imageButton_show_all.setOnClickListener {
            startActivity(Intent(this@MainActivity, ParticipatedProjectListActivity::class.java))
        }

        edittext_input_participate_code.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val participatedCode = edittext_input_participate_code.text.toString()

                RetrofitClient.create(RequestProject::class.java)
                    .joinProjectUsingCode(JoinProjectUsingCodeModel(1, participatedCode))
                    .enqueue(object : Callback<ResponseJoinProjectUsingCode> {

                        override fun onFailure(call: Call<ResponseJoinProjectUsingCode>, t: Throwable) {
                            Log.e("enterProject", "failed : $t")
                            Log.e("enterProject", "userIdx : ${preference.getUserIdx()!!}," +
                                    " code: ${edittext_input_participate_code.text}")
                        }

                        override fun onResponse(call: Call<ResponseJoinProjectUsingCode>,
                                                response: Response<ResponseJoinProjectUsingCode>) {
                            if (response.isSuccessful) {
                                if (response.body()!!.success) {
                                    Log.d("enterProject", "projectIdx : ${response.body()!!.data.projectIdx}}")

                                    preference.setProjectIdx(response.body()!!.data.projectIdx)
                                    preference.setProjectCode(edittext_input_participate_code.text.toString())

                                    moveToHostRoundActivity()
                                }
                            }
                        }
                    })
            }
            return@setOnKeyListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    fun moveToAddProject(){
        iamgeview_storming_bacground.setOnClickListener {
            val intent = Intent(this,AddProjectActivity::class.java)
            startActivity(intent)

        }
    }

    private fun loadProjectsDatas() {

        RetrofitClient.create(RequestProject::class.java).requestParticipatedProject(1)
            .enqueue(object: Callback<ResponseParticipatedProject> {
                override fun onFailure(call: Call<ResponseParticipatedProject>, t: Throwable) {
                    Log.d("requestParticipatedPj", "fail : ${t.message}")
                }

                override fun onResponse(call: Call<ResponseParticipatedProject>, response: Response<ResponseParticipatedProject>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val data = response.body()!!.data

                            showProjectList(data)
                        }
                    }
                }
            })
    }

    private fun showProjectList(data: List<ParticipatedProjectModel>) {
        if(data.isNotEmpty()){
            recentProjectsAdapter.addAll(data)
            group_main_noprojectlist.visibility = View.GONE
            recycler_participated_projects_list.visibility = View.VISIBLE
        } else {
            group_main_noprojectlist.visibility = View.VISIBLE
            recycler_participated_projects_list.visibility = View.GONE
        }
    }

    private fun moveToHostRoundActivity() {
        val intent = Intent(this, MemberRoundWaitingActivity::class.java)
        startActivity(intent)
    }
}
package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.project.network.response.ResponseJoinProjectUsingCode
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.ProjectRepository
import com.stormers.storm.project.adapter.ProjectPreviewAdapter
import com.stormers.storm.project.model.*
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var recentProjectsAdapter: ProjectPreviewAdapter

    private val projectRepository: ProjectRepository by lazy { ProjectRepository.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalApplication.run {
            userIdx = preference.getUserIdx()!!
            currentRound = null
            currentProject = null
            isHost = false
        }

        initView()

        initListener()

    }

    private fun initListener() {
        iamgeview_storming_bacground.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)
            startActivity(intent)
        }
        
        imageButton_show_all.setOnClickListener {
            startActivity(Intent(this@MainActivity, ParticipatedProjectListActivity::class.java))
        }

        edittext_input_participate_code.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                enterProject(edittext_input_participate_code.text.toString())
            }
            return@setOnKeyListener true
        }
    }

    override fun onResume() {
        super.onResume()
        loadProjectPreviews()
    }

    private fun enterProject(participatedCode: String) {

        RetrofitClient.create(RequestProject::class.java)
            .joinProjectUsingCode(JoinProjectUsingCodeModel(preference.getUserIdx()!!, participatedCode))
            .enqueue(object : Callback<ResponseJoinProjectUsingCode> {

                override fun onFailure(call: Call<ResponseJoinProjectUsingCode>, t: Throwable) {
                    Log.e("enterProject", "fail : ${t.message}")
                }

                override fun onResponse(call: Call<ResponseJoinProjectUsingCode>,
                                        response: Response<ResponseJoinProjectUsingCode>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val projectIdx = response.body()!!.data.projectIdx
                            Log.d(TAG, "enterProject: success, projectIdx : ${projectIdx}}")

                            GlobalApplication.currentProject = ProjectModel(projectIdx, edittext_input_participate_code.text.toString(),
                                null, null, null, null)

                            startActivity(Intent(this@MainActivity, MemberRoundWaitingActivity::class.java))
                        } else {
                            Log.d(TAG, "enterProject: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "enterProject: Not success, ${response.message()}")
                    }
                }
            })
    }

    private fun initView() {
        stormtoolbar_main.setMyPageButton()

        recentProjectsAdapter = ProjectPreviewAdapter(true, object : ProjectPreviewAdapter.OnProjectClickListener {
            override fun onProjectClick(projectIdx: Int) {
                val intent  = Intent(this@MainActivity,ParticipatedProjectDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                startActivity(intent)
            }
        })

        recycler_participated_projects_list.adapter = recentProjectsAdapter
        recycler_participated_projects_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_participated_projects_list.addItemDecoration(MarginDecoration(baseContext,16,RecyclerView.HORIZONTAL))
    }

    private fun loadProjectPreviews() {
        projectRepository.getPreviewAll(object: ProjectRepository.LoadProjectPreviewCallback {
            override fun onPreviewLoaded(projects: List<ProjectPreviewModel>) {
                recentProjectsAdapter.setList(projects)
                group_main_noprojectlist.visibility = View.GONE
                recycler_participated_projects_list.visibility = View.VISIBLE
            }

            override fun onDataNotAvailable() {
                group_main_noprojectlist.visibility = View.VISIBLE
                recycler_participated_projects_list.visibility = View.GONE
            }
        })
    }
}
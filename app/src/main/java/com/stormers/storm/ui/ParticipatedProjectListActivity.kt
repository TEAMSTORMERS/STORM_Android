package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.stormers.storm.R
import com.stormers.storm.project.model.RecentProjectsModel
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.project.model.ResponseParticipatedProject
import com.stormers.storm.project.network.ProjectInterface
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.Arrays.asList

class ParticipatedProjectListActivity : BaseActivity() {

    private lateinit var participatedProjectListAdapter : ParticipatedProjectListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_list)

        participatedProjectListAdapter = ParticipatedProjectListAdapter(false, object:
            ParticipatedProjectListAdapter.OnProjectClickListener {
            override fun onProjectClick(projectIdx: Int) {
                val intent = Intent(this@ParticipatedProjectListActivity, ParticipatedProjectDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                startActivity(intent)
            }
        })

        recyclerview_participatedproject.run {
            adapter = participatedProjectListAdapter
            addItemDecoration(MarginDecoration(this@ParticipatedProjectListActivity,
                2, 20, 30))
        }

        loadProjectsDatas()
    }

    private fun loadProjectsDatas() {
        //Todo: 로그인 이후 userId를 입력하여야 함
        preference.setUserId(1)

        RetrofitClient.create(ProjectInterface::class.java).requestParticipatedProject(preference.getUserId()!!)
            .enqueue(object: Callback<ResponseParticipatedProject> {
                override fun onFailure(call: Call<ResponseParticipatedProject>, t: Throwable) {
                    Log.d("requestParticipatedPj", "fail : ${t.message}")
                }

                override fun onResponse(call: Call<ResponseParticipatedProject>, response: Response<ResponseParticipatedProject>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val data = response.body()!!.data

                            participatedProjectListAdapter.addAll(data)
                        }
                    }
                }
            })
    }
}
package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.data.source.ProjectRepository
import com.stormers.storm.project.adapter.ProjectPreviewAdapter
import com.stormers.storm.project.model.ProjectPreviewModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_list.*

class ParticipatedProjectListActivity : BaseActivity() {

    companion object {
        private const val TAG = "ParticipatedProjectListActivity"
    }

    private lateinit var projectPreviewAdapter : ProjectPreviewAdapter

    private val projectRepository: ProjectRepository by lazy { ProjectRepository.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_list)

        stormtoolbar_participatedproject.setBackButton()
        
        projectPreviewAdapter = ProjectPreviewAdapter(false, object:
            ProjectPreviewAdapter.OnProjectClickListener {
            override fun onProjectClick(projectIdx: Int) {
                val intent = Intent(this@ParticipatedProjectListActivity, ParticipatedProjectDetailActivity::class.java)
                intent.putExtra("projectIdx", projectIdx)
                startActivity(intent)
            }
        })

        recyclerview_participatedproject.run {
            adapter = projectPreviewAdapter
            addItemDecoration(MarginDecoration(this@ParticipatedProjectListActivity,
                2, 20, 30))
        }

        loadProjectsDatas()
    }

    private fun loadProjectsDatas() {
        projectRepository.getPreviewAll(object: ProjectRepository.LoadProjectPreviewCallback {
            override fun onPreviewLoaded(projects: List<ProjectPreviewModel>) {
                projectPreviewAdapter.setList(projects)
            }

            @SuppressLint("LongLogTag")
            override fun onDataNotAvailable() {
                Log.e(TAG, "No project data.")
            }
        })
    }
}
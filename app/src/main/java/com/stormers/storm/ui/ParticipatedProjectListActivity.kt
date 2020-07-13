package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import com.stormers.storm.R
import com.stormers.storm.project.model.RecentProjectsModel
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_participated_project_list.*
import java.util.*
import java.util.Arrays.asList

class ParticipatedProjectListActivity : BaseActivity() {
    private lateinit var participatedProjectListAdapter : ParticipatedProjectListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participated_project_list)

        participatedProjectListAdapter = ParticipatedProjectListAdapter(object: OnProjectClickListener {
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

        participatedProjectListAdapter.addAll(loadProjectsDatas())
    }

    interface OnProjectClickListener {
        fun onProjectClick(projectIdx: Int)
    }

    //더미 데이터
    private fun loadProjectsDatas() : MutableList<ParticipatedProjectModel>{

        val datas = mutableListOf<ParticipatedProjectModel>()

        datas.apply {
            add(
                ParticipatedProjectModel(0, "평화의 브레인 스토밍",
                    listOf(
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4",
                        "https://avatars2.githubusercontent.com/u/52772787?s=460&u=4a9f12ef174f88ec143b70f4fcaaa8f1b2d87b43&v=4",
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4")
                )
            )
            add(
                ParticipatedProjectModel(1, "성규의 브레인 스토밍",
                    listOf(
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4",
                        "https://avatars2.githubusercontent.com/u/52772787?s=460&u=4a9f12ef174f88ec143b70f4fcaaa8f1b2d87b43&v=4",
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4")
                )
            )
            add(
                ParticipatedProjectModel(2, "희원이의 브레인 스토밍",
                    listOf(
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4"
                      )
                )
            )
            add(
                ParticipatedProjectModel(3, "평화의 브레인 스토밍2",
                    listOf(
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4"
                        )
                )
            )
            add(
                ParticipatedProjectModel(4, "성규의 브레인 스토밍2",
                    listOf()
                )
            )
            add(
                ParticipatedProjectModel(5, "희원이의 브레인 스토밍3",
                    listOf(
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4",
                        "https://avatars2.githubusercontent.com/u/52772787?s=460&u=4a9f12ef174f88ec143b70f4fcaaa8f1b2d87b43&v=4",
                        "https://avatars2.githubusercontent.com/u/57310034?s=460&u=3b6de8b863bdc2b902bf6cfe080bc8d34e93c348&v=4",
                        "https://avatars1.githubusercontent.com/u/56873136?s=400&v=4")
                )
            )
        }

        return datas
    }
}
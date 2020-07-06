package com.stormers.storm.projectcardlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.projectcardlist.recyclerview.ProjectCardListAdapter
import com.stormers.storm.projectcardlist.recyclerview.ProjectCardListData
import kotlinx.android.synthetic.main.activity_project_cardlist.*

class ProjectCardlistActivity : AppCompatActivity() {

    lateinit var projectCardListAdapter : ProjectCardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_cardlist)

        projectCardListAdapter = ProjectCardListAdapter()
        RecyclerView_project_cardlist.adapter = projectCardListAdapter
        RecyclerView_project_cardlist.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        projectCardlistDatas()

    }


    private fun projectCardlistDatas() {

        val datas = mutableListOf<ProjectCardListData>()

        datas.apply {
            add(
                ProjectCardListData(
                    Textview_project_title_roundinfo = "베개와 유리병의 공통점은?"
                )
            )
            add(
                ProjectCardListData(
                    Textview_project_title_roundinfo = "베개와 유리병의 공통점은?"
                )
            )
            add(
                ProjectCardListData(
                    Textview_project_title_roundinfo = "베개와 유리병의 공통점은?"
                )
            )
            add(
                ProjectCardListData(
                    Textview_project_title_roundinfo = "베개와 유리병의 공통점은?"
                )
            )
        }
        projectCardListAdapter.addAll(datas)

    }
}
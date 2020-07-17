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

        val mainview_toolbar = findViewById(R.id.include_main_toolbar) as Toolbar

        setSupportActionBar(mainview_toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.mainview_ic_bamburgerbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerlayout_main, mainview_toolbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerClosed(view: View){
                super.onDrawerClosed(view)
            }
            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
            }
        }

        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerlayout_main.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

navigationview_main.setNavigationItemSelectedListener{menuItem ->
            when(menuItem.itemId){
                R.id.item1 -> Toast.makeText(this,"item1 selected", Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(this,"item2 selected", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(this,"item3 selected", Toast.LENGTH_SHORT).show()
            }
            drawerlayout_main.closeDrawer(GravityCompat.START)
            true
        }

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

        RetrofitClient.create(RequestProject::class.java).requestParticipatedProject(preference.getUserIdx()!!)
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
            imageview_mainview_symbol.visibility = View.GONE
            textview_info_project_list.visibility = View.GONE
            recycler_participated_projects_list.visibility = View.VISIBLE
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode){

            KeyEvent.KEYCODE_ENTER -> {

                RetrofitClient.create(RequestProject::class.java)
                    .joinProjectUsingCode(JoinProjectUsingCodeModel(preference.getUserIdx()!!, edittext_input_participate_code.text.toString()))
                    .enqueue(object : Callback<ResponseJoinProjectUsingCode> {

                        override fun onFailure(call: Call<ResponseJoinProjectUsingCode>, t: Throwable) {
                            Log.e("enterProject","failed : $t")
                            Log.e("enterProject","userIdx : ${preference.getUserIdx()!!}, code: ${edittext_input_participate_code.text}")
                        }

                        override fun onResponse(call: Call<ResponseJoinProjectUsingCode>, response: Response<ResponseJoinProjectUsingCode>) {
                            if (response.isSuccessful){
                                if (response.body()!!.success){
                                    Log.d("enterProject", "projectIdx : ${response.body()!!.data.projectIdx}}")

                                    preference.setProjectIdx(response.body()!!.data.projectIdx)
                                    preference.setProjectCode(edittext_input_participate_code.text.toString())

                                    moveToHostRoundActivity()
                                }
                            }

                        }
                    }
                )

                true
            } else -> super.onKeyUp(keyCode, event)
        }
    }
    private fun moveToHostRoundActivity() {
        val intent = Intent(this, MemberRoundWaitingActivity::class.java)
        startActivity(intent)
    }
}
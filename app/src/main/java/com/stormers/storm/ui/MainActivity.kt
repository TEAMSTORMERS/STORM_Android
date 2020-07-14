package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.stormers.storm.project.adapter.ParticipatedProjectListAdapter
import com.stormers.storm.project.model.ParticipatedProjectModel
import com.stormers.storm.project.model.RecentProjectsModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    /** 메인액티비티에서 보여주는 프로젝트 참가 목록이나, ParicipatedProjectListActivity에서
    * 보여주는 프로젝트 참가 목록이나 동일한 데이터기 때문에 동일한 어댑터를 사용했어
    **/
    private lateinit var recentProjectsAdapter: ParticipatedProjectListAdapter
    val datas = mutableListOf<RecentProjectsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainview_toolbar = findViewById(R.id.include_main_toolbar) as Toolbar

        setSupportActionBar(mainview_toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.mainview_ic_bamburgerbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerlayout_main,
            mainview_toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
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


        /**
         * Adapter를 생성할 때 이 어댑터에게 요구하고 싶은 것들을 파라미터로 넘겨주면 돼
         * ParticipatedProjectListAdapter.kt를 한 번 열어봐
         */
        recentProjectsAdapter = ParticipatedProjectListAdapter(true, object : ParticipatedProjectListAdapter.OnProjectClickListener {
            override fun onProjectClick(projectIdx: Int) {
                /**
                 * 아래처럼 액티비티 전환을 해달라는 약속을 어댑터에게 전달해주는 거야. 이것이 콜백
                 */
                val intent  = Intent(this@MainActivity,ParticipatedProjectDetailActivity::class.java)
                intent.putExtra("project_idx", projectIdx)
                startActivity(intent)
            }
        })

        recycler_participated_projects_list.adapter = recentProjectsAdapter
        recycler_participated_projects_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_participated_projects_list.addItemDecoration(MarginDecoration(baseContext,16,RecyclerView.HORIZONTAL))
        recentProjectsAdapter.addAll(loadProjectsDatas())


        showProjectList()

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

    private fun showProjectList(){
        if(loadProjectsDatas().isNotEmpty()){
            recentProjectsAdapter.addAll(loadProjectsDatas())
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
                moveToHostRoundActivity()
                true
            } else -> super.onKeyUp(keyCode, event)
        }
    }
    private fun moveToHostRoundActivity() {
        val intent = Intent(this, HostRoundActivity::class.java)
        intent.putExtra("participatecode",edittext_input_participate_code.text.toString())
        startActivity(intent)
    }
}
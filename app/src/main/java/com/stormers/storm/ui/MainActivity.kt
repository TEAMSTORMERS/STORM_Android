package com.stormers.storm.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.project.adapter.RecentProjectsAdapter
import com.stormers.storm.project.model.RecentProjectsModel
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var recentProjectsAdapter: RecentProjectsAdapter
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


        // ParticipatedProjectAdapter
        recentProjectsAdapter =
            RecentProjectsAdapter()
        recycler_participated_projects_list.adapter = recentProjectsAdapter
        recycler_participated_projects_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_participated_projects_list.addItemDecoration(MarginDecoration(baseContext,16,RecyclerView.HORIZONTAL))
        recentProjectsAdapter.addAll(loadProjectsDatas())


        showProjectList()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun loadProjectsDatas() : MutableList<RecentProjectsModel>{

        val datas = mutableListOf<RecentProjectsModel>()

        datas.apply {
            add(
                RecentProjectsModel(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    projectName = "평화의 브레인스토밍"
                )
            )
            add(
                RecentProjectsModel(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    projectName = "희원이의 브레인스토밍"
                )
            )
            add(
                RecentProjectsModel(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    projectName = "성규의 브레인스토밍"
                )
            )
            add(
                RecentProjectsModel(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    projectName = "성규의 브레인스토밍"
                )
            )
            add(
                RecentProjectsModel(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    projectName = "성규의 브레인스토밍"
                )
            )
            add(
                RecentProjectsModel(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    projectName = "성규의 브레인스토밍"
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
}
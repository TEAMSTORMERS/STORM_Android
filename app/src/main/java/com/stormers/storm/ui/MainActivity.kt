package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.project.network.RequestProject
import com.stormers.storm.project.network.response.ResponseJoinProjectUsingCode
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SocketClient
import com.stormers.storm.project.ProjectRepository
import com.stormers.storm.project.adapter.ProjectPreviewAdapter
import com.stormers.storm.project.model.*
import com.stormers.storm.project.network.response.ResponseLookupProject
import com.stormers.storm.round.model.RoundModel
import com.stormers.storm.util.DateUtils
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

    private val requestProject: RequestProject by lazy { RetrofitClient.create(RequestProject::class.java) }

    private var lookupDialogButtons = ArrayList<StormDialogButton>()

    private val errorDialogButton = ArrayList<StormDialogButton>()

    private var lookupDialogListener: StormDialogButton.OnClickListener? = null

    private var lookupDialogCallback: StormDialog.OnContentAttachedCallback? = null

    private var cacheCode: String? = null

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

        initDialogButton()
    }

    override fun onResume() {
        super.onResume()
        loadProjectPreviews()
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

    private fun initDialogButton() {
        errorDialogButton.add(StormDialogButton("확인", true, null))
    }

    private fun initListener() {
        iamgeview_storming_bacground.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)
            startActivity(intent)
        }
        
        imageButton_show_all.setOnClickListener {
            startActivity(Intent(this@MainActivity, ParticipatedProjectListActivity::class.java))
        }

        edittext_input_participate_code.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                showLookupProject(edittext_input_participate_code.text.toString())
            }
            return@setOnKeyListener true
        }
    }

    private fun showLookupProject(projectCode: String) {
        requestProject.lookupProject(projectCode).enqueue(object: Callback<ResponseLookupProject> {
            override fun onFailure(call: Call<ResponseLookupProject>, t: Throwable) {
                Log.d(TAG, "showLookupProject: Fail, $projectCode, ${t.message}")
            }

            override fun onResponse(call: Call<ResponseLookupProject>, response: Response<ResponseLookupProject>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        response.body()!!.data.let {
                            showLookupDialog(it.projectIdx, it.projectName, it.projectComment, projectCode)
                            Log.d(TAG, "showLookupProject: Success, projectIdx: ${it.projectIdx}, projectName: ${it.projectName}")
                        }
                    } else {
                        Log.d(TAG, "showLookupProject: Not success, ${response.body()!!.status}, ${response.body()!!.message}")

                        showErrorLookupComment(response.body()!!.status)
                    }
                } else {
                    Log.d(TAG, "showLookupProject: Not successful, ${response.code()}, ${response.message()}")
                }
            }
        })
    }

    private fun showErrorLookupComment(status: Int) {
        val comment = getResponseLookupComment(status)

        StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, comment)
            .setButtonArray(errorDialogButton)
            .build()
            .show(supportFragmentManager, "error_dialog")
    }

    private fun getResponseLookupComment(status: Int): String {
         return when (status) {
            202 -> "지금은 프로젝트에\n참여하실 수 없습니다.."
            204 -> "지금은 호스트가 준비 중입니다."
            400 -> "유효하지 않은 코드입니다."
            else -> "오류가 발생했습니다."
        }
    }

    private fun showLookupDialog(projectIdx: Int, projectName: String, projectComment: String, projectCode: String) {
        //확인 버튼을 눌렀을 때
        if (lookupDialogListener == null || cacheCode == null || cacheCode != projectCode) {
            cacheCode = projectCode

            lookupDialogListener = object : StormDialogButton.OnClickListener {
                override fun onClick() {
                    enterProject(projectIdx, projectCode)
                }
            }
        }

        //프로젝트 이름, 호스트 한마디 설정
        if (lookupDialogCallback == null || cacheCode == null || cacheCode != projectCode) {
            lookupDialogCallback = object : StormDialog.OnContentAttachedCallback {
                override fun onContentAttached(view: View) {
                    (view.findViewById(R.id.textview_lookupproject_name) as TextView).text = projectName
                    (view.findViewById(R.id.textview_lookupproject_comment) as TextView).text = projectComment
                }
            }
        }

        //다이얼로그 버튼이 만들어진 적이 없으면 생성
        if (lookupDialogButtons.isEmpty()) {
            lookupDialogButtons.run {
                add(StormDialogButton("취소", true, null))
                add(StormDialogButton("확인", true, lookupDialogListener))
            }
        //있으면 재사용
        } else {
            lookupDialogButtons[1].listener = lookupDialogListener
        }

        StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "프로젝트에 참여하시겠습니까?")
            .setContentRes(R.layout.layout_lookup_project)
            .setOnContentAttachedCallback(lookupDialogCallback!!)
            .setHorizontalArray(lookupDialogButtons)
            .build()
            .show(supportFragmentManager, "lookup_dialog")
    }

    private fun enterProject(projectIdx: Int, projectCode: String) {
        Log.d(TAG, "enterProject: userIdx: ${GlobalApplication.userIdx}, projectIdx: $projectIdx")
        requestProject.enterProject(EnterProjectModel(GlobalApplication.userIdx, projectIdx))
            .enqueue(object : Callback<ResponseJoinProjectUsingCode> {

                override fun onFailure(call: Call<ResponseJoinProjectUsingCode>, t: Throwable) {
                    Log.e("enterProject", "fail : ${t.message}")
                }

                override fun onResponse(call: Call<ResponseJoinProjectUsingCode>,
                                        response: Response<ResponseJoinProjectUsingCode>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val roundIdx = response.body()!!.data
                            Log.d(TAG, "enterProject: success, roundIdx : $roundIdx")

                            SocketClient.getInstance()
                            SocketClient.connection()

                            GlobalApplication.currentProject = ProjectModel(projectIdx, DateUtils.getToday(), projectCode,
                                null, null, null, null)

                            GlobalApplication.currentRound = RoundModel(roundIdx, null, null, null, null)

                            startActivity(Intent(this@MainActivity, MemberRoundWaitingActivity::class.java))
                        } else {
                            Log.d(TAG, "enterProject: Not success, ${response.body()!!.message}")
                        }
                    } else {
                        Log.d(TAG, "enterProject: Not successful, ${response.message()}")
                    }
                }
            })
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
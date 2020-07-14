package com.stormers.storm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.InterfaceAddProject
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.project.fragment.WaitingForStartingProjectFragment
import com.stormers.storm.project.model.AddProjectModel
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.view_toolbar.view.*

class AddProjectActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)

        //뒤로 가기 버튼 설정
        setSupportActionBar(include_addproject_toolbar.toolbar)

        start_project()

        //fixme: 뒤로가기 버튼 안먹는 것 같아요!!!
      /*  supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.host_a_1_btn_back)


        } */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    //메뉴 선택시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            //Fixme: 마이페이지 아이콘이 작아지는 문제 해결
            R.id.menu_toolbar_mypage -> {
                //Todo: 마이페이지로 이동하는 코드
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun start_project(){


        val buttonArray = ArrayList<StormDialogButton>()

        button_add_project.setOnClickListener {





            buttonArray.add(
                StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        val intent = Intent(this@AddProjectActivity,HostRoundWaitingActivity::class.java)
                        startActivity(intent)


                    }
                })

            )

            StormDialogBuilder(StormDialogBuilder.STORM_LOGO, "참여코드 생성 완료!")
                .setContentRes(R.layout.view_participation_code)
                .setButtonArray(buttonArray)
                .build()
                .show(supportFragmentManager, "create_participate_code")
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
 
 }
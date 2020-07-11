package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.customview.dialog.StormDialog
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.round.fragment.HostRoundSettingFragment
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.fragment_waiting_for_starting_project.*
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

            Log.d("버튼 눌림","버튼눌림")

            buttonArray.add(
                StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        Toast.makeText(applicationContext, "확인 눌렀음", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext,HostRoundActivity::class.java)
                        startActivity(intent)

                    }
                })

            )

            StormDialogBuilder(StormDialogBuilder.STORM_LOGO, "참여코드 생성 완료!")
                .setContentRes(R.layout.view_participation_code)
                .setButtonArray(buttonArray)
                .build()
                .show(supportFragmentManager, "rulereminder")
        }
    }



 }
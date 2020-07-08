package com.stormers.storm

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.view_toolbar.view.*

class AddProjectActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)

        //뒤로 가기 버튼 설장
        setSupportActionBar(include_addproject_toolbar.toolbar)

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.host_a_1_btn_back)
        }
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
}
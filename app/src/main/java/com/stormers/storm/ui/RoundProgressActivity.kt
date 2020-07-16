package com.stormers.storm.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.canvas.fragment.CanvasDrawingFragment
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_host_round_setting.*
import kotlinx.android.synthetic.main.view_toolbar.view.*

class RoundProgressActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress)

        setSupportActionBar(include_roundprogress_toolbar.toolbar)

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.host_a_1_btn_back)
        }

        //Debug 용도로 라운드 목표를 터치하면 라운드가 종료되도록 함
        this.textView_round_goal.setOnClickListener {
            startActivity(Intent(this, RoundFinishActivity::class.java))
        }

        goToFragment(AddCardFragment::class.java, null)
    }

    override fun initFragmentId(): Int? {
        return R.id.framelayout_roundprogress_fragment
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
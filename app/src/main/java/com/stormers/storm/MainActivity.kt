package com.stormers.storm

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_project.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainview_toolbar = findViewById(R.id.mainview_toolbar) as Toolbar

        setSupportActionBar(mainview_toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.mainview_ic_bamburgerbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
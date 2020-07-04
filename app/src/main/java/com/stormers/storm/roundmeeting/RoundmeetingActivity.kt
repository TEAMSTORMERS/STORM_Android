package com.stormers.storm.roundmeeting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.R
import com.stormers.storm.scrapcard.ScrapcardDetailFragment

class RoundmeetingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roundmeeting)

        addFragment(RoundmeetingFragment())
    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.constraintLayout_roundmeeting, fragment).commitAllowingStateLoss()
    }

}
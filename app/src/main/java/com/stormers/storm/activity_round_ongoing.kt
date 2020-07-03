package com.stormers.storm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.stormers.storm.RoundSetting.AddCardFragment
import com.stormers.storm.addcard.AddedCardFragment

class activity_round_ongoing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_ongoing)

        //addFragment(AddCardFragment())
        addFragment(AddedCardFragment())
    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.constraintLayout_round_ongoing, fragment).commitAllowingStateLoss()
    }

}
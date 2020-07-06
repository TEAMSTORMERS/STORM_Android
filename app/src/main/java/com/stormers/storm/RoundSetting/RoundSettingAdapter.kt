package com.stormers.storm.RoundSetting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.stormers.storm.RoundStartFragment

class RoundSettingAdapter (fm:FragmentManager):FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HostRoundSettingFragment()
            else -> RoundStartFragment()
        }
    }
    override fun getCount() =2
}
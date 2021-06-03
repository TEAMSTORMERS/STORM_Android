package com.stormers.storm.mypage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stormers.storm.R
import com.stormers.storm.base.BaseBindingFragment
import com.stormers.storm.databinding.FragmentMyPageBinding

class MyPageFragment : BaseBindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        binding.backgroundMypage.stormtoolbarMypage.setBackButton()
    }
}
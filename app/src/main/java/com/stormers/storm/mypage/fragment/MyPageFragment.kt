package com.stormers.storm.mypage.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.stormers.storm.R
import com.stormers.storm.base.BaseBindingFragment
import com.stormers.storm.databinding.FragmentMyPageBinding
import com.stormers.storm.mypage.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseBindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setToolbar()
    }

    private fun setToolbar() {
        binding.layoutBackground.stormtoolbarMypage.setBackButton()
    }
}
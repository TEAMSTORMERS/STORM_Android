package com.stormers.storm.logIn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.stormers.storm.R
import com.stormers.storm.base.BaseBindingFragment
import com.stormers.storm.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSoftInputModeToResize()
        setRootScrollView(binding.scrollviewLogin)
        binding.viewModel = loginViewModel
    }
}
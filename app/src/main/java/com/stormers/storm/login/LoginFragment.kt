package com.stormers.storm.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.stormers.storm.R
import com.stormers.storm.base.BaseBindingFragment
import com.stormers.storm.databinding.FragmentLoginBinding
import com.stormers.storm.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSoftInputModeToResize()
        setRootScrollView(binding.scrollviewLogin)
        binding.viewModel = loginViewModel

        loginViewModel.isLoginSuccessful.observe(viewLifecycleOwner) {
            //Todo: 싱글액티비티로 변환
            if (it) {
                requireActivity().run {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
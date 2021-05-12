package com.stormers.storm.logIn

import android.os.Bundle
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseBindingFragment
import com.stormers.storm.databinding.FragmentLoginBinding

class LoginFragment: BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSoftInputModeToResize()
        setRootScrollView(binding.scrollviewLogin)
    }
}
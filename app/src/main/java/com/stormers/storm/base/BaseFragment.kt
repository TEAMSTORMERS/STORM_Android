package com.stormers.storm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.stormers.storm.R

abstract class BaseFragment(@LayoutRes private val layoutResId: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container,false)
    }

    fun goToFragment(cls: Class<*>, args: Bundle?) {
        (activity as? BaseActivity)?.goToFragment(cls, args)
    }

    fun showLoadingDialog() {
        (activity as? BaseActivity)?.showLoadingDialog()
    }

    fun hideLoadingDialog() {
        (activity as? BaseActivity)?.hideLoadingDialog()
    }

}
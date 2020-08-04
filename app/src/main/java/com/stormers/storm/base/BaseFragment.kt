package com.stormers.storm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.stormers.storm.ui.GlobalApplication
import com.stormers.storm.util.SharedPreference

abstract class BaseFragment() : Fragment() {

    constructor(@LayoutRes layoutResId: Int) : this() {
        this.layoutResId = layoutResId
    }
    
    protected var TAG = javaClass.name

    @LayoutRes
    private var layoutResId: Int = -1

    protected val preference: SharedPreference by lazy { GlobalApplication.prefs }

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
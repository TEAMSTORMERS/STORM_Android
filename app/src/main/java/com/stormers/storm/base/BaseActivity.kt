package com.stormers.storm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stormers.storm.R

abstract class BaseActivity : AppCompatActivity() {

    private var fragmentId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentId = initFragmentId()
    }

    protected open fun initFragmentId() : Int? {
        return null
    }

    fun showLoadingDialog() {
        //Todo: 로딩 다이얼로그 띄우기
    }

    fun hideLoadingDialog() {
        //Todo: 로딩 다이얼로그 닫기
    }

    fun goToFragment(cls: Class<*>, args: Bundle?) {
        try {
            val fragment = cls.newInstance() as Fragment
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(fragmentId!!, fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
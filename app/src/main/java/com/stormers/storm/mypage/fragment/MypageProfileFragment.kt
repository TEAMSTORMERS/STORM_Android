package com.stormers.storm.mypageedittext_user_name.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.ui.MypageActivity
import kotlinx.android.synthetic.main.fragment_mypage_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class MypageProfileFragment : BaseFragment(R.layout.fragment_mypage_profile) {
    val FLAG_REQ_STORAGE = 102

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagebutton_mypage_edit.setOnClickListener {
            textview_user_name_input.visibility = View.INVISIBLE
            edittext_user_name.visibility = View.VISIBLE
        }

        settingPermission()

        circleImageView_camera_button.setOnClickListener {
            selectGallery()
        }
    }

    fun settingPermission() {
        var permis = object : PermissionListener {
            override fun onPermissionGranted() {
                Log.d("PermissionGranted","권한 허가")
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Log.d("PermissionDenied", "권한 거부")
            }
        }

        TedPermission.with(context).setPermissionListener(permis)
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_STORAGE -> {
                    val uri = data?.data
                    circleimageview_mypage_profile.setImageURI(uri)
                }
            }
        }
    }

    private fun selectGallery() {
        var writePermission = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {

        } else {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, FLAG_REQ_STORAGE)
        }

    }

}
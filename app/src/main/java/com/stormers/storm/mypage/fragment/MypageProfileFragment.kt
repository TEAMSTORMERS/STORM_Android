package com.stormers.storm.mypage.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.ui.MypageActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.bottomsheet_select_profile.*
import kotlinx.android.synthetic.main.fragment_mypage_profile.*
import kotlinx.android.synthetic.main.fragment_mypage_profile.view.*
import java.io.File
import java.io.IOException
import java.security.Key
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import java.util.regex.Pattern

class MypageProfileFragment : BaseFragment(R.layout.fragment_mypage_profile) {
    val FLAG_REQ_STORAGE = 102
    var char_limit = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edittext_user_name.isEnabled = false

        edittext_user_name.filters = Array(1) {textSetFilter()}

        imagebutton_mypage_edit.setOnClickListener {

            edittext_user_name.isEnabled = true

            edittext_user_name.requestFocus()
            edittext_user_name.isFocusable = true
            edittext_user_name.isCursorVisible = true
            edittext_user_name.setSelection(edittext_user_name.length())
            edittext_user_name.setTextColor(ContextCompat.getColor(context!!, R.color.storm_popup_line_gray))
            view_mypage.setBackgroundColor(ContextCompat.getColor(context!!, R.color.storm_red))

            //키보드 올리기
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

        edittext_user_name.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KEYCODE_ENTER || keyCode == EditorInfo.IME_ACTION_DONE) && !char_limit) {
                edittext_user_name.setTextColor(ContextCompat.getColor(context!!, R.color.storm_gray))
                edittext_user_name.clearFocus()
                edittext_user_name.isCursorVisible = false
                view_mypage.setBackgroundColor(ContextCompat.getColor(context!!, R.color.storm_popup_gray))

                //키보드 내리기
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edittext_user_name.windowToken, 0)

                Toast.makeText(context, "사용자 이름이 변경되었습니다.", Toast.LENGTH_SHORT).show()

                edittext_user_name.isEnabled = false
                true
            }
            false
        }

        edittext_user_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 2) {
                    textview_notice.visibility = View.VISIBLE
                    char_limit = true
                }
                else {
                    textview_notice.visibility = View.INVISIBLE
                    char_limit = false
                }
            }

        })

        settingPermission()

        /*circleImageView_camera_button.setOnClickListener {
            selectGallery()
        }
        circleimageview_mypage_profile.setOnClickListener {
            selectGallery()
        }*/

        //BottomSheet
        val bottomSheetChangeProfile = BottomSheetBehavior.from(bottomsheet_profile_select_mypage)
        bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetChangeProfile.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        view_bottom_sheet_blur_mypage.visibility = View.VISIBLE
                        bottomsheet_profile_select_mypage.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        view_bottom_sheet_blur_mypage.visibility = View.INVISIBLE
                        bottomsheet_profile_select_mypage.visibility = View.INVISIBLE
                    }
                }
            }
        })

        circleImageView_camera_button.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_COLLAPSED

            button_gallery.setOnClickListener{
                selectGallery()
                settingPermission()
                bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        view_bottom_sheet_blur_mypage.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
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

    fun textSetFilter(): InputFilter {
        val pattern = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]*$")
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            if (!pattern.matcher(source).matches()) {
                Toast.makeText(context, "특수문자는 입력불가", Toast.LENGTH_SHORT).show()
                return@InputFilter ""
            }
            null
        }
        return filter

        /*val filter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i : Int in start..end) {
                if (!Character.isLetterOrDigit(source.get(i))) {
                    return@InputFilter ""
                }
            }
            null
        }
        return filter*/
    }

}
package com.stormers.storm.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.bottomsheet_select_profile.*
import kotlinx.android.synthetic.main.fragment_mypage_profile.*

class SiginUpActivity : AppCompatActivity() {

    val change_background = GradientDrawable()
    val FLAG_REQ_STORAGE = 102


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_up)

        var edittext_name = findViewById<EditText>(R.id.edittext_name_signup)

        selectProfileColor()

        //사용자 이름 textview 적용
        edittext_name.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (edittext_name.text.length != 0){
                    if (edittext_name.text.length >= 2){
                        var first_two_characters = edittext_name.text.substring(0,2)
                        textview_name_in_profile.setText(first_two_characters)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        change_profile()

    }

    // 프로필 default image color변경
    fun selectProfileColor(){

        imagebutton_select_profile_purple.setOnClickListener{
            imagebutton_select_profile_purple.visibility = View.INVISIBLE
            imagebutton_select_profile_purple_checked.visibility = View.VISIBLE

            imagebutton_select_profile_yellow.visibility = View.VISIBLE
            imagebutton_select_profile_yellow_checked.visibility = View.INVISIBLE

            imagebutton_select_profile_red.visibility = View.VISIBLE
            imagebutton_select_profile_red_checked.visibility = View.INVISIBLE

            //라운딩 및 프로필 색 변환
            imageview_profile_signup.background = ShapeDrawable(OvalShape())
            imageview_profile_signup.clipToOutline = true
            change_background.setColor(resources.getColor(R.color.storm_purple))
            imageview_profile_signup.setImageDrawable(change_background)


        }

        imagebutton_select_profile_red.setOnClickListener{
            imagebutton_select_profile_red.visibility = View.INVISIBLE
            imagebutton_select_profile_red_checked.visibility = View.VISIBLE

            imagebutton_select_profile_purple.visibility = View.VISIBLE
            imagebutton_select_profile_purple_checked.visibility = View.INVISIBLE

            imagebutton_select_profile_yellow.visibility = View.VISIBLE
            imagebutton_select_profile_yellow_checked.visibility = View.INVISIBLE

            imageview_profile_signup.background = ShapeDrawable(OvalShape())
            imageview_profile_signup.clipToOutline = true
            change_background.setColor(resources.getColor(R.color.storm_red))
            imageview_profile_signup.setImageDrawable(change_background)

        }

        imagebutton_select_profile_yellow.setOnClickListener{
            imagebutton_select_profile_yellow.visibility = View.INVISIBLE
            imagebutton_select_profile_yellow_checked.visibility = View.VISIBLE

            imagebutton_select_profile_purple.visibility = View.VISIBLE
            imagebutton_select_profile_purple_checked.visibility = View.INVISIBLE

            imagebutton_select_profile_red.visibility = View.VISIBLE
            imagebutton_select_profile_red_checked.visibility = View.INVISIBLE

            imageview_profile_signup.background = ShapeDrawable(OvalShape())
            imageview_profile_signup.clipToOutline = true
            change_background.setColor(resources.getColor(R.color.storm_yellow))
            imageview_profile_signup.setImageDrawable(change_background)
        }
    }

    // 프로필 사진 선택 BottomSheet
    fun change_profile(){
        val bottomSheetChangeProfile : BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomsheet_profile_select)

        bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetChangeProfile.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        view_bottom_sheet_blur.visibility = View.VISIBLE
                        bottomsheet_profile_select.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        view_bottom_sheet_blur.visibility = View.INVISIBLE
                        bottomsheet_profile_select.visibility = View.INVISIBLE
                    }
                }
            }
        })

        imagebutton_set_profile.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_COLLAPSED

            button_gallery.setOnClickListener{
                selectGallery()
                settingPermission()
            }

            button_change_default_image.setOnClickListener{
                textview_name_in_profile.visibility = View.VISIBLE
                imageview_profile_signup.setImageResource(R.drawable.profile_circle)
                bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN

            }
        }

        view_bottom_sheet_blur.setOnClickListener{
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

        TedPermission.with(applicationContext).setPermissionListener(permis)
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val bottomSheetChangeProfile : BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomsheet_profile_select)
        bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_STORAGE -> {
                    val uri = data?.data
                    imageview_profile_signup.background = ShapeDrawable(OvalShape())
                    imageview_profile_signup.clipToOutline = true
                    imageview_profile_signup.setImageURI(uri)
                    bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
                    textview_name_in_profile.visibility = View.GONE
                }
            }
        }
    }

    private fun selectGallery() {
        var writePermission = ContextCompat.checkSelfPermission(applicationContext!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission = ContextCompat.checkSelfPermission(applicationContext!!, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {

        } else {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, FLAG_REQ_STORAGE)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
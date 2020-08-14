package com.stormers.storm.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.bottomsheet_select_profile.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SignUpActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SignUpActivity"
        private const val FLAG_REQ_STORAGE = 102
    }

    private val changeBackground = GradientDrawable()

    private lateinit var profile : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_up)

        selectProfileColor()

        setNameTextWatcher()

        changeProfile()

        goToLogInActivity()
    }

    // 프로필 default image color변경
    private fun selectProfileColor(){

            imagebutton_select_profile_purple.setOnClickListener{
                imagebutton_select_profile_purple.setBackgroundResource(R.drawable.join_profile_selected_purple)

                imagebutton_select_profile_yellow.setBackgroundResource(R.drawable.join_profile_yellow)

                imagebutton_select_profile_red.setBackgroundResource(R.drawable.join_profile_red)

                //라운딩 및 프로필 색 변환
                constraintlayout_signup_profile.background = ShapeDrawable(OvalShape())
                constraintlayout_signup_profile.clipToOutline = true
                changeBackground.setColor(resources.getColor(R.color.storm_purple))
                imageview_signup_profilebackground.setImageDrawable(changeBackground)


            }

            imagebutton_select_profile_red.setOnClickListener{
                imagebutton_select_profile_purple.setBackgroundResource(R.drawable.join_profile_purple)

                imagebutton_select_profile_yellow.setBackgroundResource(R.drawable.join_profile_yellow)

                imagebutton_select_profile_red.setBackgroundResource(R.drawable.join_profile_selected_red)

                constraintlayout_signup_profile.background = ShapeDrawable(OvalShape())
                constraintlayout_signup_profile.clipToOutline = true
                changeBackground.setColor(resources.getColor(R.color.storm_red))
                imageview_signup_profilebackground.setImageDrawable(changeBackground)

            }

            imagebutton_select_profile_yellow.setOnClickListener{
                imagebutton_select_profile_purple.setBackgroundResource(R.drawable.join_profile_purple)

                imagebutton_select_profile_yellow.setBackgroundResource(R.drawable.join_profile_selected_yellow)

                imagebutton_select_profile_red.setBackgroundResource(R.drawable.join_profile_red)

                constraintlayout_signup_profile.background = ShapeDrawable(OvalShape())
                constraintlayout_signup_profile.clipToOutline = true
                changeBackground.setColor(resources.getColor(R.color.storm_yellow))
                imageview_signup_profilebackground.setImageDrawable(changeBackground)
            }
        }


    // 프로필 사진 선택 BottomSheet
    private fun changeProfile() {
        val bottomSheetChangeProfile : BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomsheet_profile_select)

        bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetChangeProfile.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    view_bottom_sheet_blur.visibility = View.VISIBLE
                    bottomsheet_profile_select.visibility = View.VISIBLE

                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    view_bottom_sheet_blur.visibility = View.INVISIBLE
                    bottomsheet_profile_select.visibility = View.INVISIBLE
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
                imageview_signup_profilebackground.setImageResource(R.drawable.profile_circle)
                bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        view_bottom_sheet_blur.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun settingPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Log.d(TAG,"settingPermission() : PermissionGranted")
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Log.d(TAG, "settingPermission() : PermissionDenied")
            }
        }

        TedPermission.with(applicationContext).setPermissionListener(permissionListener)
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
                    constraintlayout_signup_profile.background = ShapeDrawable(OvalShape())
                    constraintlayout_signup_profile.clipToOutline = true
                    imageview_signup_profilebackground.setImageURI(uri)
                    bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
                    textview_name_in_profile.visibility = View.GONE
                }
            }
        }
    }

    private fun selectGallery() {
        val writePermission = ContextCompat.checkSelfPermission(applicationContext!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(applicationContext!!, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "selectGallery() : Permission is denied")
        } else {
            val intent = Intent(Intent.ACTION_PICK)
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

    fun setNameTextWatcher(){
        edittext_name_signup.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                val first_two_characters = edittext_name_signup.text!!.substring(0, edittext_name_signup.text!!.length)
                textview_name_in_profile.text = first_two_characters

                if (edittext_name_signup.text!!.length < 2){
                    textview_input_more_than_two_char.visibility = View.VISIBLE
                } else {
                    textview_input_more_than_two_char.visibility = View.GONE

                    button_complete_signup.setOnClickListener {
                        saveProfile()
                        val intent = Intent(this@SignUpActivity, SetEmailPasswordActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    private fun saveProfile() {

        val profileRootLayout = constraintlayout_signup_profile
        profileRootLayout.isDrawingCacheEnabled = true
        profileRootLayout.buildDrawingCache()
        val profileBitmap = profileRootLayout.drawingCache
        textview_name_in_profile.visibility = View.INVISIBLE
        imageview_signup_profilebackground.setImageDrawable(BitmapDrawable(resources, profileBitmap))

        //Todo 비트맵 서버로 전송
    }

    fun goToLogInActivity() {
        button_back_signup.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}
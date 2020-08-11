package com.stormers.storm.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.bottomsheet_select_profile.*

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

        val editTextName = findViewById<EditText>(R.id.edittext_name_signup)

        selectProfileColor()

        //사용자 이름 textview 적용
        editTextName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if (editTextName.text.length < 2){
                    textview_input_more_than_two_char.visibility = View.VISIBLE
                } else {
                    textview_input_more_than_two_char.visibility = View.GONE
                }
                val first_two_characters = editTextName.text.substring(0, editTextName.text.length)
                textview_name_in_profile.text = first_two_characters
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
        changeProfile()

        goToNextSignUpPage()
    }

    // 프로필 default image color변경
    private fun selectProfileColor(){

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
                changeBackground.setColor(resources.getColor(R.color.storm_purple))
                imageview_profile_signup.setImageDrawable(changeBackground)


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
                changeBackground.setColor(resources.getColor(R.color.storm_red))
                imageview_profile_signup.setImageDrawable(changeBackground)

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
                changeBackground.setColor(resources.getColor(R.color.storm_yellow))
                imageview_profile_signup.setImageDrawable(changeBackground)
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
                imageview_profile_signup.setImageResource(R.drawable.profile_circle)
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

    private fun goToNextSignUpPage(){

        button_next_signup.setOnClickListener{
            when {
                edittext_name_signup.text.isNullOrBlank() -> {
                    Toast.makeText(this,"이름을 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
                edittext_name_signup.text.length < 2 -> {
                    Toast.makeText(this,"이름을 2글자 이상 입력해주세요",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    saveProfile()
                }
            }
        }
    }

    private fun saveProfile() {

//        val tvBitmap = textview_name_in_profile.getDrawingCache()
//        val ivBitmap = imageview_profile_signup.getDrawingCache()
//
//        val tvProfileBitmap = Bitmap.createBitmap(tvBitmap.width, tvBitmap.height, tvBitmap.config)
//        val ivProfileBitmap = Bitmap.createBitmap(ivBitmap.width, ivBitmap.height, ivBitmap.config)
//        val canvas = Canvas()
//
//        canvas.drawBitmap(tvProfileBitmap, Matrix(),null)
//        canvas.drawBitmap(ivProfileBitmap, Matrix(), null)

        val tvName = findViewById<TextView>(R.id.textview_name_in_profile)
        val ivProfileBackground = findViewById<ImageView>(R.id.imageview_profile_signup)

        tvName.buildDrawingCache(true)
        val namebitmap : Bitmap = tvName.getDrawingCache(true).copy(Bitmap.Config.ARGB_8888, false)
        tvName.destroyDrawingCache()

        ivProfileBackground.buildDrawingCache(true)
        val profileBackgroundBitmap : Bitmap = ivProfileBackground.getDrawingCache(true).copy(Bitmap.Config.ARGB_8888, false)
        ivProfileBackground.destroyDrawingCache()

        val canvas = Canvas()
        canvas.drawBitmap(profileBackgroundBitmap, Matrix(), null)
        canvas.drawBitmap(namebitmap, Matrix(), null)

        textview_name_in_profile.visibility = View.INVISIBLE
        imageview_profile_signup.draw(canvas)

//        val intent = Intent(this, SetEmailPasswordActivity::class.java)
//        startActivity(intent)
    }

//    fun getBitmapFromText() : Bitmap? {
//        var width = 0
//        var height = 0
//        width = windowManager.defaultDisplay.width
//        height = windowManager.defaultDisplay.height
//        var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        var canvas = Canvas(bitmap)
//        var profile : Canvas
//          textview_name_in_profile.draw(canvas)
//        var iv = imageview_profile_signup.draw(canvas)
//
//
//        return bitmap
//    }


}
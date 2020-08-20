package com.stormers.storm.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import com.stormers.storm.SignUp.InterfaceSignUp
import com.stormers.storm.SignUp.ResponseSignUpModel
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_set_email_password.*
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.activity_sigin_up.button_back_signup
import kotlinx.android.synthetic.main.bottomsheet_select_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SignUpActivity : BaseActivity() {

    companion object {
        private const val TAG = "SignUpActivity"
        private const val FLAG_REQ_STORAGE = 102
        private const val FLAG_PERM_STORAGE = 99
        const val IS_DEFAULT_IMAGE = 0
        const val USER_IMAGE = 1
    }

    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val changeBackground = GradientDrawable()

    private lateinit var profile : ImageView

    val buttonArray = ArrayList<StormDialogButton>()

    lateinit var profileRootLayout : ConstraintLayout

    lateinit var profileBitmap : Bitmap

    var userImageFlag = IS_DEFAULT_IMAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_up)

        selectProfileColor()

        setNameTextWatcher()

        changeProfile()

        goCompleteSignUpActivity()

        goToLogInActivity()
    }

    // 프로필 default image color변경
    private fun selectProfileColor(){

            imagebutton_select_profile_purple.setOnClickListener{
                changeProfileResources(imagebutton_select_profile_purple)
                keepBackgroundShapeAndSetColor(R.color.storm_purple)
            }

            imagebutton_select_profile_red.setOnClickListener{
                changeProfileResources(imagebutton_select_profile_red)
                keepBackgroundShapeAndSetColor(R.color.storm_red)

            }

            imagebutton_select_profile_yellow.setOnClickListener{
                changeProfileResources(imagebutton_select_profile_yellow)
                keepBackgroundShapeAndSetColor(R.color.storm_yellow)
            }
        }
    fun keepBackgroundShapeAndSetColor(colorId: Int) {
        constraintlayout_signup_profile.background = ShapeDrawable(OvalShape())
        constraintlayout_signup_profile.clipToOutline = true
        changeBackground.setColor(resources.getColor(colorId))
        imageview_signup_profilebackground.setImageDrawable(changeBackground)
    }


    fun changeProfileDefaultBackground() {
        if(imagebutton_select_profile_purple.background.equals(R.drawable.join_profile_selected_purple)){
            keepBackgroundShapeAndSetColor(R.color.storm_purple)
        } else {
            if(imagebutton_select_profile_purple.background.equals(R.drawable.join_profile_selected_yellow)){
                keepBackgroundShapeAndSetColor(R.color.storm_yellow)
            } else {
                if (imagebutton_select_profile_purple.background.equals(R.drawable.join_profile_selected_red)){
                    keepBackgroundShapeAndSetColor(R.color.storm_red)
                }
            }
        }
    }

    fun changeProfileResources(imageButtonSelected : ImageButton) {

        if(imageButtonSelected == imagebutton_select_profile_purple) {
            imagebutton_select_profile_purple.setBackgroundResource(R.drawable.join_profile_selected_purple)
            imagebutton_select_profile_yellow.setBackgroundResource(R.drawable.join_profile_yellow)
            imagebutton_select_profile_red.setBackgroundResource(R.drawable.join_profile_red)
        } else {
            if(imageButtonSelected == imagebutton_select_profile_yellow) {
                imagebutton_select_profile_purple.setBackgroundResource(R.drawable.join_profile_purple)
                imagebutton_select_profile_yellow.setBackgroundResource(R.drawable.join_profile_selected_yellow)
                imagebutton_select_profile_red.setBackgroundResource(R.drawable.join_profile_red)
            }  else {
                if(imageButtonSelected == imagebutton_select_profile_red) {
                    imagebutton_select_profile_purple.setBackgroundResource(R.drawable.join_profile_purple)
                    imagebutton_select_profile_yellow.setBackgroundResource(R.drawable.join_profile_yellow)
                    imagebutton_select_profile_red.setBackgroundResource(R.drawable.join_profile_selected_red)
                }
            }
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

        }

        button_gallery.setOnClickListener{
            if(checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)){
                openGallery()
            }
        }

        button_change_default_image.setOnClickListener{
            textview_name_in_profile.visibility = View.VISIBLE

            imageview_signup_profilebackground.setImageResource(R.drawable.circular_profile)
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN

            changeProfileResources(imagebutton_select_profile_purple)

            imagebutton_select_profile_purple.visibility = View.VISIBLE
            imagebutton_select_profile_yellow.visibility = View.VISIBLE
            imagebutton_select_profile_red.visibility = View.VISIBLE

            userImageFlag = IS_DEFAULT_IMAGE
            changeProfileDefaultBackground()
        }

        view_bottom_sheet_blur.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
        }
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

                    userImageFlag = USER_IMAGE

                    bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
                    textview_name_in_profile.visibility = View.GONE
                }
            }
        }
    }

    //갤러리 전환
    private fun openGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, FLAG_REQ_STORAGE)

            imagebutton_select_profile_purple.visibility = View.GONE
            imagebutton_select_profile_yellow.visibility = View.GONE
            imagebutton_select_profile_red.visibility = View.GONE
    }

    //갤러리 권한처리 메서드
    fun checkPermission(permissions: Array<out String>, flag: Int) :Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(permission in permissions) {
                if(ContextCompat.checkSelfPermission(this, permission) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, flag)
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            FLAG_PERM_STORAGE -> {
                for (grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "저장소 권한을 승인해야 프로필 사진을 설정할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                openGallery()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setNameTextWatcher() {

        edittext_name_signup.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                val first_two_characters = edittext_name_signup.text!!.substring(0, edittext_name_signup.text!!.length)
                textview_name_in_profile.text = first_two_characters

                if (edittext_name_signup.text!!.length < 2){
                    textview_input_more_than_two_char.visibility = View.VISIBLE
                    button_complete_signup.setBackgroundResource(R.drawable.button_color_popup_line_gray)
                    button_complete_signup.isEnabled = false
                } else {
                    textview_input_more_than_two_char.visibility = View.GONE
                    button_complete_signup.setBackgroundResource(R.drawable.box_red_radius)
                    button_complete_signup.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    private fun saveProfile() {

        profileRootLayout = constraintlayout_signup_profile
        profileRootLayout.isDrawingCacheEnabled = true
        profileRootLayout.buildDrawingCache()

        profileBitmap = profileRootLayout.drawingCache
        textview_name_in_profile.visibility = View.INVISIBLE
        imageview_signup_profilebackground.setImageDrawable(BitmapDrawable(resources, profileBitmap))
    }

    fun goToLogInActivity() {

        buttonArray.add(
            StormDialogButton("취소", true, object : StormDialogButton.OnClickListener{
                override fun onClick() {
                    Log.d("회원가입진행","회원가입진행")
                }
            })
        )

        buttonArray.add(
            StormDialogButton("확인", true, object : StormDialogButton.OnClickListener{
                override fun onClick() {
                    startActivity(Intent(this@SignUpActivity, SetEmailPasswordActivity::class.java))
                    finish()
                }
            })
        )

        button_back_signup.setOnClickListener {
            StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "입력한 내용이 삭제됩니다.\n뒤로 가시겠습니까?")
                .setHorizontalArray(buttonArray)
                .build()
                .show(supportFragmentManager, "cancle SignUp")
        }

    }


    fun goCompleteSignUpActivity() {

        button_complete_signup.setOnClickListener{

            saveProfile()
            GlobalApplication.profileBitmap = profileBitmap

            val fileUserImage = BitmapConverter.bitmapToFile(GlobalApplication.profileBitmap!! , this.cacheDir.toString())

            val requestUserImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileUserImage!!)

            val sendUserImage = MultipartBody.Part.createFormData("user_img", fileUserImage.name, requestUserImageFile)

            val userName = RequestBody.create(MediaType.parse("text/plain"), edittext_name_signup.text.toString())

            val userEmail = RequestBody.create(MediaType.parse("text/plain"), intent.getStringExtra("userEmail"))

            val userPassword = RequestBody.create(MediaType.parse("text/plain"), intent.getStringExtra("userPassword"))

            val userImageFlag = RequestBody.create(MediaType.parse("text/plain"), userImageFlag.toString())

            RetrofitClient.create(InterfaceSignUp::class.java).interfaceSignUp(sendUserImage, userName,userEmail,userPassword, userImageFlag)
                .enqueue(object : Callback<ResponseSignUpModel> {
                    override fun onFailure(call: Call<ResponseSignUpModel>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<ResponseSignUpModel>,
                        response: Response<ResponseSignUpModel>
                    ) {
                        if(response.isSuccessful){
                            if (response.body()!!.success){

                                val intent = Intent(this@SignUpActivity, CompleteSignUpActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                if(response.body()!!.status == 400) {
                                    Log.d("정보누락", response.body()!!.status.toString())
                                } else {
                                    if (response.body()!!.status == 600){
                                        Log.d("중복이메일, DB오류", response.body()!!.status.toString())
                                        textview_email_warning.setText("이미 사용중인 이메일입니다.")
                                        textview_email_warning.visibility = View.VISIBLE
                                    } else {
                                        Log.d("서버오류", response.body()!!.status.toString())
                                    }
                                }
                            }
                        } else {
                            Log.d("서버통신 오류", response.message())
                        }
                    }
                })
        }
    }
}
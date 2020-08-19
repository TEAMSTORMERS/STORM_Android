package com.stormers.storm.mypage.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.contentcapture.ContentCaptureContext
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentResolverCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.stormers.storm.R
import com.stormers.storm.base.BaseFragment
import com.stormers.storm.canvas.network.RequestCard
import com.stormers.storm.customview.dialog.StormDialogBuilder
import com.stormers.storm.customview.dialog.StormDialogButton
import com.stormers.storm.mypage.network.MypageData
import com.stormers.storm.mypage.network.MypageInterface
import com.stormers.storm.mypage.network.ResponseMypageData
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.network.SimpleResponse
import com.stormers.storm.ui.MypageWithdrawalActivity
import com.stormers.storm.ui.SignUpActivity
import kotlinx.android.synthetic.main.activity_sigin_up.*
import kotlinx.android.synthetic.main.bottomsheet_select_profile.*
import kotlinx.android.synthetic.main.fragment_mypage_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.jar.Manifest
import java.util.regex.Pattern

class MypageProfileFragment : BaseFragment(R.layout.fragment_mypage_profile) {

    val FLAG_REQ_STORAGE = 102
    val FLAG_PERM_STORAGE = 99
    val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var char_limit = false

    private val changeBackground = GradientDrawable()

    private lateinit var bottomSheetChangeProfile: BottomSheetBehavior<View>

    private lateinit var retrofitClient: MypageInterface

    var userIdx = -1

    //image flag를 나타내는 상수
    companion object {
        private const val USER_IMAGE = 1
        private const val USER_DEFAULT_IMAGE = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        circleImageView_camera_button.background = ShapeDrawable(OvalShape())
        circleImageView_camera_button.clipToOutline = true

        imageview_mypage_default_image.background = ShapeDrawable(OvalShape())
        imageview_mypage_default_image.clipToOutline = true

        userIdx = preference.getUserIdx()!!

        retrofitClient = RetrofitClient.create(MypageInterface::class.java)

        retrofitClient.responseMypageData(userIdx).enqueue(object :
            Callback<ResponseMypageData> {
            override fun onFailure(call: Call<ResponseMypageData>, t: Throwable) {
                if (t.message != null) {
                    Log.d("MypageProfileFragment", t.message!!)
                } else {
                    Log.d("MypageProfileFragment", "통신실패")
                }
            }

            override fun onResponse(
                call: Call<ResponseMypageData>,
                response: Response<ResponseMypageData>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        Log.d("MypageProfileFragment", "user_idx : ${response.body()!!}")

                        //서버로부터 받아온 user name 적용
                        edittext_user_name.setText(response.body()!!.data.user_name)

                        //서버로부터 받아온 profile image 적용: 앨범에서 사진을 설정했을 경우
                        //Todo: boolean 값으로 변경하기
                        if (response.body()!!.data.user_img_flag == USER_IMAGE) {
                            constraint_select_button.visibility = View.INVISIBLE
                            textview_mypage_name_in_profile.visibility = View.INVISIBLE

                            Glide.with(context!!).load(response.body()!!.data.user_img)
                                .into(imageview_mypage_default_image)
                        }
                        //서버로부터 받아온 profile image 적용: 기본 이미지일 경우
                       else {
                            selectProfileColor()

                            //초기 기본이미지 text 설정
                            if (edittext_user_name.text.length >= 2) {
                                var first_two_characters = edittext_user_name.text.substring(0,2)
                                textview_mypage_name_in_profile.setText(first_two_characters)
                            }
                        }

                    } else {
                        Log.d("MypageProfileFragment", "통신실패")
                    }
                } else {
                    Log.d("MypageProfileFragment", "${response.message()}, ${response.errorBody()}")
                }
            }

        })

        edittext_user_name.isEnabled = false

        edittext_user_name.filters = Array(1) {textSetFilter()}

        constraint_mypage_edit.setOnClickListener {

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
                updateName(edittext_user_name.text.toString())

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
                if (edittext_user_name.text.isNotEmpty()){
                    if (edittext_user_name.text.length >= 2){
                        var first_two_characters = edittext_user_name.text.substring(0,2)
                        textview_mypage_name_in_profile.setText(first_two_characters)
                    }
                    else {
                        textview_mypage_name_in_profile.setText(edittext_user_name.text)
                    }
                }
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

        //BottomSheet
        bottomSheetChangeProfile = BottomSheetBehavior.from(bottomsheet_profile_select_mypage)
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


        val cameraButtonClickListener = View.OnClickListener {
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_COLLAPSED

            button_gallery.setOnClickListener{
                if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {
                    selectGallery()
                }
            }


            //bottomSheet에서 기본이미지로 변경 버튼 누를 경우
           button_change_default_image.setOnClickListener {
               constraint_select_button.visibility = View.VISIBLE
               textview_mypage_name_in_profile.visibility = View.VISIBLE

               selectPurpleButton()

               bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        //카메라 버튼 선택
        circleImageView_camera_button.setOnClickListener(cameraButtonClickListener)
        imageview_mypage_default_image.setOnClickListener(cameraButtonClickListener)

        view_bottom_sheet_blur_mypage.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
        }

        //회원 탈퇴 activity로 이동
        constraint_withdrawa.setOnClickListener {
            val nextIntent = Intent(context!!, MypageWithdrawalActivity::class.java)
            startActivity(nextIntent)
        }

        //로그아웃 다이얼로그
        constraint_logout.setOnClickListener {
            val buttonArray = ArrayList<StormDialogButton>()

            buttonArray.add(
                StormDialogButton("취소", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        //Todo: 다이얼로그의 취소 버튼을 눌렀을 때의 동작
                    }
                })
            )

            buttonArray.add(
                StormDialogButton("확인", true, object : StormDialogButton.OnClickListener {
                    override fun onClick() {
                        //Todo: 다이얼로그의 확인 버튼을 눌렀을 때의 동작
                    }
                })
            )

            fragmentManager?.let {
                StormDialogBuilder(StormDialogBuilder.THUNDER_LOGO, "로그아웃 하시겠습니까?")
                    .setHorizontalArray(buttonArray)
                    .build()
                    .show(it, "logout")
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_STORAGE -> {
                    val uri = data?.data

                    //앨범에서 가져온 사진으로 변경
                    imageview_mypage_default_image.background = ShapeDrawable(OvalShape())
                    imageview_mypage_default_image.clipToOutline = true

                    textview_mypage_name_in_profile.visibility = View.INVISIBLE
                    constraint_select_button.visibility = View.INVISIBLE

                    imageview_mypage_default_image.setImageURI(uri)
                }
            }
        }
    }

    private fun selectGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)

        bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
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
    }

    private fun selectProfileColor() {

        imagebutton_mypage_select_purple.setOnClickListener{
            selectPurpleButton()
        }

        imagebutton_mypage_select_red.setOnClickListener{
            selectRedButton()
        }

        imagebutton_mypage_select_yellow.setOnClickListener{
            selectYellowButton()
        }
    }

    private fun selectPurpleButton() {
        imagebutton_mypage_select_purple.setBackgroundResource(R.drawable.join_profile_selected_purple)
        imagebutton_mypage_select_yellow.setBackgroundResource(R.drawable.join_profile_yellow)
        imagebutton_mypage_select_red.setBackgroundResource(R.drawable.join_profile_red)

        //라운딩 및 프로필 색 변환
        constraint_mypage_default.background = ShapeDrawable(OvalShape())
        constraint_mypage_default.clipToOutline = true
        changeBackground.setColor(resources.getColor(R.color.storm_purple))
        imageview_mypage_default_image.setImageDrawable(changeBackground)
    }

    private fun selectRedButton() {
        imagebutton_mypage_select_purple.setBackgroundResource(R.drawable.join_profile_purple)
        imagebutton_mypage_select_yellow.setBackgroundResource(R.drawable.join_profile_yellow)
        imagebutton_mypage_select_red.setBackgroundResource(R.drawable.join_profile_selected_red)

        constraint_mypage_default.background = ShapeDrawable(OvalShape())
        constraint_mypage_default.clipToOutline = true
        changeBackground.setColor(resources.getColor(R.color.storm_red))
        imageview_mypage_default_image.setImageDrawable(changeBackground)
    }

    private fun selectYellowButton() {
        imagebutton_mypage_select_purple.setBackgroundResource(R.drawable.join_profile_purple)
        imagebutton_mypage_select_yellow.setBackgroundResource(R.drawable.join_profile_selected_yellow)
        imagebutton_mypage_select_red.setBackgroundResource(R.drawable.join_profile_red)

        constraint_mypage_default.background = ShapeDrawable(OvalShape())
        constraint_mypage_default.clipToOutline = true
        changeBackground.setColor(resources.getColor(R.color.storm_yellow))
        imageview_mypage_default_image.setImageDrawable(changeBackground)
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

    //권한처리 메서드
    fun checkPermission(permissions: Array<out String>, flag: Int) :Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(permission in permissions) {
                if(ContextCompat.checkSelfPermission(context!!, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, flag)
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
        when (requestCode) {
            FLAG_PERM_STORAGE -> {
                for (grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "저장소 권한을 승인해야 프로필 사진을 설정할 수 있습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                selectGallery()
            }
        }
    }

    fun updateName(name : String) {
        val user_idx = userIdx
        val user_name = name

        retrofitClient.updateMypageName(user_idx, user_name)
            .enqueue(object : Callback<SimpleResponse> {
                override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                    if (t.message != null) {
                        Log.d("MypageProfileNameUpdate", t.message!!)
                    } else {
                        Log.d("MypageProfileNameUpdate", "통신실패")
                    }
                }

                override fun onResponse(
                    call: Call<SimpleResponse>,
                    response: Response<SimpleResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            Log.d("MypageProfileNameUpdate", "user_idx : ${response.body()!!}")
                        } else {
                            Log.d("MypageProfileNameUpdate", "통신실패")
                        }
                    } else {
                        Log.d("MypageProfileNameUpdate", "${response.message()}, ${response.errorBody()}")
                    }
                }

            })

    }
}
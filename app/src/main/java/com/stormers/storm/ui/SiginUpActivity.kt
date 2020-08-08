package com.stormers.storm.ui

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sigin_up.*

class SiginUpActivity : AppCompatActivity() {

    val change_background = GradientDrawable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin_up)

        var edittext_name = findViewById<EditText>(R.id.edittext_name_signup)

        selectProfileColor()

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
        }

        view_bottom_sheet_blur.setOnClickListener{
            bottomSheetChangeProfile.state = BottomSheetBehavior.STATE_HIDDEN
        }


    }
}
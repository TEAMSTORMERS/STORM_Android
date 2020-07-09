package com.stormers.storm.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.auth.AuthType

import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.auth.helper.Base64
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.Utility.getKeyHash
import com.kakao.util.helper.Utility.getPackageInfo

import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(){
    //fixme: 여기에 문제가 있을까???? 그리고 결정적으로 왜 LoginActivity에서만 kakaoSDK가 import가 안돼,,,
    private var callback: SessionCallback = SessionCallback()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 99 //private const val TAG = "GoogleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //fixme: 여기에 문제가 있을까????
        //Kakao 로그인 연동
        imagebutton_login_kakao.setOnClickListener {

            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL,this)
            Session.getCurrentSession().addCallback(callback)
            //Debug 용도로 일단 메인화면으로 이동하게 함
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            this@LoginActivity.finish()
        }

        //Google Firebase 로그인
        imagebutton_login_google.setOnClickListener{signIn()}

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        initView()

    }

    //fixme: 여기에 문제가 있을까????
    //Kakao
    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }
    //Lottie 애니메이션 로그인뷰
    private fun initView(){

        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login) as LottieAnimationView
        animationView.setAnimation("login_bg.json")
        animationView.loop(true)
        animationView.playAnimation()

    }


    //Firebase
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    } //onStart End

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Kakao Session
        //fixme: 여기에 문제가 있을까????
        if (Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            Log.i("Log","session get current session")
            return
        }

        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    } // onActivityResult End


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                    toMainActivity(firebaseAuth?.currentUser)
                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                    Snackbar.make(constraintlayout_login, "로그인에 실패하였습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
    }// firebaseAuthWithGoogle END

    fun toMainActivity(user: FirebaseUser?) {
        if(user !=null) { // MainActivity 로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    // signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

}

package com.stormers.storm.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
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
import com.kakao.auth.Session
import com.kakao.util.helper.Utility.getPackageInfo
import com.stormers.storm.R
import com.stormers.storm.SignUp.InterfaceSignUp
import com.stormers.storm.SignUp.ResponseSignUpModel
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.kakao.SessionCallback
import com.stormers.storm.network.RetrofitClient
import com.stormers.storm.util.SharedPreference.Companion.USER_IDX
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : BaseActivity() {

    private lateinit var callback: SessionCallback
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 99 //private const val TAG = "GoogleActivity"
    val user = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT > 9) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        //Todo: 카카오 로그인이랑 구글 로그인이 짬뽕 되어 있어서 유지보수가 어려우니 구분 지어 작성하거나 메서드 이름이라도 잘 바꿔보자 !


        var hash_key = getKeyHash(this)
        Log.i("KaKaoLogin", hash_key) // 확인

        val startIntent = {
            startActivity(Intent(this, MainActivity::class.java))
        }

        callback = SessionCallback(startIntent)
        imagebutton_login_kakao.setOnClickListener {
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this)
            Log.d("KaKaoLogin", "버튼 눌림")
            Session.getCurrentSession().addCallback(callback)
        }
        //Google Firebase 로그인
        imagebutton_login_google.setOnClickListener {
            signIn()
            Log.d("GoogleLogIn", "버튼 눌림")
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        initView()

    }

    // Key_hash 구하기
    fun getKeyHash(context: Context): String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())

                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {
                Log.w("Log", "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }

    //Kakao
    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    //Firebase
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account !== null) { // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    } //onStart End

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Kakao Session
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.i("Log", "session get current session")
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

    companion object{
        lateinit var userImgUrl: Bitmap

    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser

                    Log.e("ttest in --- ", "${user!!.photoUrl}")

                    var userImgUrl: Bitmap  = BitmapConverter.urlToBitmap(user!!.photoUrl.toString())
                    //UrlTask().execute(user!!.photoUrl.toString())


                    val userImgBitmap = BitmapConverter.bitmapToFile(
                        userImgUrl,
                        this@LoginActivity!!.cacheDir.toString()
                    )

                    val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), userImgBitmap!!)
                    val userUploadImage = MultipartBody.Part.createFormData("user_img", userImgBitmap.name, requestFile)

                    Log.e(
                        "file-----",
                        "userImgBitmap - ${userImgBitmap}, requestFile - ${requestFile}"
                    )


                    val userName = RequestBody.create(
                        MediaType.parse("text/plain"),
                        user!!.displayName.toString()
                    )

                    val userTokenGoogle =
                        RequestBody.create(MediaType.parse("text/plain"), acct.idToken.toString())


                    RetrofitClient.create(InterfaceSignUp::class.java).interfaceSignUp(
                        userName, userTokenGoogle, null, userUploadImage
                    )
                        .enqueue(object : Callback<ResponseSignUpModel> {
                            override fun onFailure(call: Call<ResponseSignUpModel>, t: Throwable) {
                                Log.d("SignUp Google", "${t}")
                            }

                            override fun onResponse(
                                call: Call<ResponseSignUpModel>,
                                response: Response<ResponseSignUpModel>
                            ) {
                                if(response.isSuccessful){
                                    if(response.body()!!.success){
                                        Log.d("SignUp 통신성공", "통신성공")

                                        preference.setUserIdx(response.body()!!.data.toString().toInt())

                                        Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                                        toMainActivity(firebaseAuth?.currentUser)
                                    }
                                }

                            }
                        })

                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                    Snackbar.make(constraintlayout_login, "로그인에 실패하였습니다.", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }// firebaseAuthWithGoogle END

    fun toMainActivity(user: FirebaseUser?) {
        if (user != null) { // MainActivity 로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    // signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //Lottie 애니메이션 로그인뷰
    private fun initView() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login)
        animationView.setAnimation("login_bg.json")
        animationView.repeatCount = INFINITE
        animationView.playAnimation()
    }

    inner class UrlTask : AsyncTask<String,Process,Bitmap>(){
        override fun doInBackground(vararg userUrl: String): Bitmap {

            Log.e("Thread in ", "in")
            try {
                Log.e("null success ?", "success")
                BitmapConverter.urlToBitmap(userUrl[0]).toString()
            }catch (e : Exception){
                Log.e("null failed ?", e.toString())
            }finally {
                Log.e("null failed ?", "finally")

            }

            return BitmapConverter.urlToBitmap(userUrl[0])
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)

            Log.e("result","test")
            userImgUrl = result!!
        }
    }

}

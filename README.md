
# ⚡️🌪STORM Android🌪⚡️


![서비스소개](https://user-images.githubusercontent.com/55133871/86811476-ec591d00-c0b8-11ea-971e-c78793429ae7.png)

**실시간 브레인스토밍 협업 플랫폼**



## (A-1항목) ConstraintLayout을 이용한 xml🔗


**1️⃣ constraintLayout의 다양한 속성 활용**

**✅ Fragment에 들어갈 뷰들은 위아래가 잘라먹힐 가능성이 있어 모두 chain으로 묶은 뒤 bias 값을 조정하였음**


**✅ View의 width, height 값을 constraint_percent 속성으로 적용하였기 때문에 대부분 match_constraint를 사용하였음**


**✅ width나 height값을 constraint_percent로 조정한 뒤, constraintDimensionRatio 속성으로 나머지 값을 결정하였음**


**(fragment_host_round_setting.xml / fragment_round_meeting_expend.xml / fragment_round_start.xml / fragment_waiting_for_starting_project.xml / etc.)**

**ex) fragment_host_round_setting.xm (chain속성)**

  
	 <ImageView  
	  android:id="@+id/imageview_symbol"  
	  android:layout_width="0dp"  
	  android:layout_height="0dp"  
	  android:src="@drawable/host_b_1_symbol"  
	  app:layout_constraintBottom_toTopOf="@+id/textview_roundnumber"  
	  app:layout_constraintEnd_toEndOf="parent"  
	  app:layout_constraintHeight_percent="0.158"  
	  app:layout_constraintStart_toStartOf="parent"  
	  app:layout_constraintTop_toTopOf="parent"  
	  app:layout_constraintVertical_bias="0.74"  
	  app:layout_constraintVertical_chainStyle="packed"  
	  app:layout_constraintWidth_percent="0.144" />  
	  
	<TextView  
	  android:id="@+id/textview_roundnumber"  
	  android:layout_width="wrap_content"  
	  android:layout_height="wrap_content"  
	  android:fontFamily="@font/notosans_bold"  
	  android:text="ROUND 1"  
	  android:textSize="15sp"  
	  android:layout_marginStart="23dp"  
	  android:layout_marginTop="38dp"  
	  app:layout_constraintBottom_toTopOf="@+id/textview_round_goal"  
	  app:layout_constraintStart_toStartOf="parent"  
	  app:layout_constraintTop_toBottomOf="@+id/imageview_symbol" />

**ex) activity_main.xml (constraintHeight_percent)**
          
         <ImageView  
          android:id="@+id/imageView_background_red_radius"  
          android:layout_width="match_parent"  
          android:layout_height="0dp"  
          android:background="@drawable/background_red_radius"  
          android:elevation="10dp"  
          app:layout_constraintEnd_toEndOf="parent"  
          app:layout_constraintHeight_percent="0.548"  
          app:layout_constraintHorizontal_bias="0.0"  
          app:layout_constraintStart_toStartOf="parent"  
          app:layout_constraintTop_toTopOf="parent" />



**2️⃣ 제약조건의 연관성**

**✅ 화면의 크기의 변화가 있어도 겹쳐지지 말아야할 뷰들은 서로 제약 조건을 갖도록 하였음**

**✅ 뷰의 위치는 부모보다는 최대한 가까운 뷰에게 제약조건을 걸고 여백을 통해 지정하였음**

**✅ 제약 조건을 주지 않아도 위치가 결정되는 경우가 있었지만 각 뷰의 상대적 위치를 확실하게 하기 위해 가까운 뷰와 제약 조건을 가짐**


**3️⃣ match_parent, wrap_content, match_constraint의 사용**

**- GridRecyclerView의 item으로 들어갈 layout들은 해당 속성을 사용하기 어려워 길이 값을 하드코딩하였음**

**- 그 외에는 해당 속성을 모두 사용하였음**


 **ConstraintLayout을 사용하지 않은 레이아웃**
 
- [ ] **CustomView를 위한 레이아웃**
 
	**➡️ view_card_custom.xml**

	**➡️ view_botton_custom.xml**

 - [ ] **ViewPager2의 아이템 레이아웃 (최상위 레이아웃의 가로세로가 match_parent가 아니면 예외가 발생하여 FrameLayout으로 해결)**

	**➡️ item_round_info_card.xml**

	**➡️ item_card_detail.xml**

## (A-2항목) Kotlin collection의 확장함수 사용 / Custom 확장함수 사용📝


## (A-3항목) Library & Purpose📖

**Glide 이미지 로딩 라이브러리**

     implementation "com.github.bumptech.glide:glide:4.10.0"
     kapt "com.github.bumptech.glide:compiler:4.10.0"

**Material 디자인 라이브러리(NavigationView 디자인 사용)**

    implementation 'com.google.android.material:material:1.0.0'

**실시간 통신을 위한 Socket 라이브러리**

    implementation ('io.socket:socket.io-client:1.0.0') { exclude group: 'org.json', module: 'json' }

**서버통신을 위한 Retrofit 라이브러리**

    implementation 'com.google.code.gson:gson:2.8.6'  
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'  
    implementation 'com.squareup.retrofit2:converter-gson:2.7.1'

**Firebase SDK Google 로그인 연동**

    implementation 'com.google.firebase:firebase-analytics:17.2.2'  
    implementation 'com.google.firebase:firebase-auth:18.0.0'  
    implementation 'com.google.android.gms:play-services-auth:18.0.0'


**Kakao  SDK로그인 연동**

    implementation group: project.KAKAO_SDK_GROUP, name: 'usermgmt', version: project.KAKAO_SDK_VERSION

**Splash, 라운드 대기중 로딩 화면 구현을 위한 Lottie 라이브러리**

    implementation 'com.airbnb.android:lottie:3.4.1'

**내부 Database 구축을위한 Room 라이브러리**

    implementation "androidx.room:room-runtime:2.2.5"

			

## (A-3항목) Project Structure📐

 


## (A-3항목) Core Function & How to build💻

## 🚪Kakao api, Google api 사용

**firebaseAuth, googleSignInClient, user, RC_SIGN_IN 객체를 전역으로 선언**

    private lateinit var firebaseAuth: FirebaseAuth  
    private lateinit var googleSignInClient: GoogleSignInClient  
    private val RC_SIGN_IN = 99 //private const val TAG = "GoogleActivity"  
    val user = FirebaseAuth.getInstance().currentUser**

**GoogleSignInOption객체를 구성할 때 requestIdToken을 호출
로그인 버튼을 눌렀을 때 signIn 함수 실행되고 구글 계정 인증 Activity가 실행**

    imagebutton_login_google.setOnClickListener {  
      signIn()  
        Log.d("GoogleLogIn", "버튼 눌림")  
    }  
      
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)  
        .requestIdToken(getString(R.string.default_web_client_id))  
        .requestEmail()  
        .build()
    
    
    private fun signIn() {  
        val signInIntent = googleSignInClient.signInIntent  
      startActivityForResult(signInIntent, RC_SIGN_IN)  
    }

**로그인이 정상적으로 수행되고 requestCode가 RC_SIGN_IN이면 firebaseWithGoogle함수 호출**

	if (requestCode == RC_SIGN_IN) {  
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)  
    try {  
		  val account = task.getResult(ApiException::class.java)  
	      firebaseAuthWithGoogle(account!!)  
		  } catch (e: ApiException) {  
			  Log.w("LoginActivity", "Google sign in failed", e)  
      }  
	}

**GoogleSignInAccount객체에서 IdToken을 가져와  FirebaseAuth로 교환하고 인증**
**➡️ 성공적으로 수행되면 RetrofitClient를 통해서 서버로 userName, userTokenGoogle, userUploadUser 송신**

	private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {  
	    Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)  
  
	  val credential = GoogleAuthProvider.getCredential(acct.idToken, null)  
  
    firebaseAuth.signInWithCredential(credential)  
        .addOnCompleteListener(this) { task ->  
	  if (task.isSuccessful) {  
                val user = FirebaseAuth.getInstance().currentUser  
  
  
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
	}
	
	fun toMainActivity(user: FirebaseUser?) {  
	    if (user != null) { // MainActivity 로 이동  
	  startActivity(Intent(this, MainActivity::class.java))  
	        finish()  
	    }  
	}

 **Kakao api**

 **- Application을 상속하는 GlobalAppication.kt**
 **- KakaoAdapter를 상속하는 KakaoSDKAdapter**
 **- SessionCallback.kt를 별도로 생성**
 
**<SessionCallback.kt>**
**SessionCallback.kt에서 로그인 세션이 성공했을 때와 실패했을 때의 행동을 정의**

    override fun onSessionOpenFailed(exception: KakaoException?) {  
        Log.e("KaKaoLogin","Session Call back :: onSessionOpenFailed ${exception?.message}")  
    }  
    override fun onSessionOpened() {  
      
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {  
      
            override fun onFailure(erroResult: ErrorResult?) {  
                Log.i("KaKaoLogin", "Session Call back:: on failed ${erroResult?.errorMessage}")  
            }  
      
            override fun onSessionClosed(errorResult: ErrorResult?) {  
                Log.i("KaKaoLogin", "Session Call back:: on Closed ${errorResult?.errorMessage}")  
            }  
      
            override fun onSuccess(result: MeV2Response?) {  
                Log.d("KaKaoLogin","성공했습니다.")  
                startInetnt()  
                checkNotNull(result) { "session response null" }  
      }  
        })  
    }

	

**🎨Drawing기능 구현**

**💾Drawing 파일처리 및 저장**

**📶socket 통신**

## **🎥Animation 적용**

**1️⃣ Lottie Animation**
  **: 스플래쉬, 로그인, 라운드 대기 중 로딩**

    private fun initView() {  
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login)  
        animationView.setAnimation("login_bg.json")  
        animationView.repeatCount = INFINITE  
      animationView.playAnimation()  
    }

**📢Notification**



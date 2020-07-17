
# ⚡️🌪STORM Android🌪⚡️


![서비스소개](https://user-images.githubusercontent.com/55133871/86811476-ec591d00-c0b8-11ea-971e-c78793429ae7.png)

**실시간 브레인스토밍 협업 플랫폼**



## (A-1항목) ConstraintLayout을 이용한 xml🔗


**1️⃣ constraintLayout의 다양한 속성 활용**


✅ Fragment에 들어갈 뷰들은 위아래가 잘라먹힐 가능성이 있어 모두 chain으로 묶은 뒤 bias 값을 조정하였음


✅ View의 width, height 값을 constraint_percent 속성으로 적용하였기 때문에 대부분 match_constraint를 사용하였음


✅ width나 height값을 constraint_percent로 조정한 뒤, constraintDimensionRatio 속성으로 나머지 값을 결정하였음


(fragment_host_round_setting.xml / fragment_round_meeting_expend.xml / fragment_round_start.xml / fragment_waiting_for_starting_project.xml / etc.)

<br>

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

<br>

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

<br>

**2️⃣ 제약조건의 연관성**


✅ 화면의 크기의 변화가 있어도 겹쳐지지 말아야할 뷰들은 서로 제약 조건을 갖도록 하였음

✅ 뷰의 위치는 부모보다는 최대한 가까운 뷰에게 제약조건을 걸고 여백을 통해 지정하였음

✅ 제약 조건을 주지 않아도 위치가 결정되는 경우가 있었지만 각 뷰의 상대적 위치를 확실하게 하기 위해 가까운 뷰와 제약 조건을 가짐

<br>

**3️⃣ match_parent, wrap_content, match_constraint의 사용**

- GridRecyclerView의 item으로 들어갈 layout들은 해당 속성을 사용하기 어려워 길이 값을 하드코딩하였음

- 그 외에는 해당 속성을 모두 사용하였음

<br>

 **ConstraintLayout을 사용하지 않은 레이아웃**
 
- [ ] **CustomView를 위한 레이아웃**
 
	➡️ view_card_custom.xml

	➡️ view_botton_custom.xml

 - [ ] **ViewPager2의 아이템 레이아웃 (최상위 레이아웃의 가로세로가 match_parent가 아니면 예외가 발생하여 FrameLayout으로 해결)**

	➡️ item_round_info_card.xml

	➡️ item_card_detail.xml

<br><br>

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
    
**drawing 관련 라이브러리**

    implementation 'com.github.gcacace:signature-pad:1.3.1'
			
<br><br>

## (A-3항목) Project Structure📐

 


(A-3항목) Core Function & How to build💻

**🚪로그인 Kakao api, Google api 사용**

 <br><br>

**🎨Drawing기능 구현**

**CanvasDrawingFragment.kt**

    private var isDrew = false
    
    override fun initCanvas() {
        signaturepad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                isDrew = true
            }

            override fun onClear() {
                isDrew = false
            }

            override fun onSigned() {
                //Doing nothing. prevent error.
                signaturepad
            }
        })
    }

    override fun onTrashed() {
        signaturepad.clear()
    }
    
📌 OnSignedListener를 view에 설정
  - onStartSigning() : pad를 터치했을 때 isDrew의 값이 true로 변경
  - onClear() : pad에 그려진 내용을 지울 때 이벤트 발생
  - onTrached() : pad에 그려진 그림을 전체 삭제

<br><br>

**💾Drawing 파일처리 및 저장**

✔️ 그린 그림을 DB에 저장

**CanvasDrawingFragment.kt**

    private fun saveCardIntoDB(bitmap: Bitmap) {
        savedCardRepository.insert(
            SavedCardEntity(preference.getProjectIdx()!!, preference.getRoundIdx()!!, SavedCardEntity.FALSE, SavedCardEntity.DRAWING,
                BitmapConverter.bitmapToString(bitmap), null
            )
        )
    }
    
<br>

✔️ 비트맵을 문자열로 변환하여 DB에 저장

**BitmapConverter.kt**

    object BitmapConverter {

        private const val TAG = "BitmapConverter"
        private const val QUALITY = 70

        // String -> Bitmap
        fun stringToBitmap(encodedString: String?): Bitmap? {
            return try {
                val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                null
            }
        }

        //Bitmap -> String
        fun bitmapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, baos)

            val bytes: ByteArray = baos.toByteArray()
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }

        //Bitmap -> ByteArray
        fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos)
            return baos.toByteArray()
        }
    } 

<br><br>

**📶socket 통신**

## **🎥Animation 적용**

**1️⃣ Lottie Animation**
  **: 스플래쉬, 로그인, 라운드 대기 중 로딩**
  
📌 로그인 애니메이션

    private fun initView() {  
        val animationView = findViewById<LottieAnimationView>(R.id.lottieanimation_login)  
        animationView.setAnimation("login_bg.json")  
        animationView.repeatCount = INFINITE  
      animationView.playAnimation()  
    }
    
<br>
    
📌 라운드 대기 중 로딩 애니메이션

    <com.airbnb.lottie.LottieAnimationView
                android:id="@+id/lottieAnimationView"
                android:layout_width="45dp"
                android:layout_height="15dp"
                android:layout_marginStart="10dp"
                app:layout_constraintBottom_toBottomOf="@+id/textview_round_ready"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toEndOf="@+id/textview_round_ready"
                app:layout_constraintTop_toTopOf="@+id/textview_round_ready"
                app:lottie_autoPlay="true"
                app:lottie_rawRes="@raw/loading_animation"
                app:lottie_loop="true" />
		
<br>

📌 스플래쉬 애니메이션

<br>

**📢Notification**




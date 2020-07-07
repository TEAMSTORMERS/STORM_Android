# STORM_Android


<img src = "https://user-images.githubusercontent.com/56873136/86507620-a1b77680-be14-11ea-841e-74861b7c588e.png" width = "40%">

**STORM**

**- 실시간 브레인스토밍 협업 플랫폼**



## (A-1항목) ConstraintLayout을 이용한 xml🔗
(A-1 ConstraintLayout을 사용한 화면 개발.  
(별도 레이아웃 사용시 이유가 명확해야 함)  
  
- 아래 항목 모두 포함되어야 하나로 인정  
1. match_constraint, chain, guideLine 등 constraintLayout의 다양한 속성 활용  
2. 제약조건은 연관성이 있어야 함(모든 View를 부모와 연관지어 margin100dp..와 같은 방식 X)  
3. width, height 속성에 dp단위 적용은 필요한 경우 아니면 match_parent, wrap_content, match_constraint 위주로 사용할 것  
-> 어디에 사용했는지 README에 표시해주시기 바랍니다.)

### CustomView, ViewPager2의 ItemLayout을 제외하고는 모두 CosntraintLayout으로 제작
1. constraintLayout의 다양한 속성 활용
- Fragment에 들어갈 뷰들은 위아래가 잘라먹힐 가능성이 있어 모두 chain으로 묶은 뒤 bias 값을 조정하였음
- View의 width, height 값을 constraint_percent 속성으로 적용하였기 때문에 대부분 match_constraint를 사용하였음
- width나 height값을 constraint_percent로 조정한 뒤, constraintDimensionRatio 속성으로 나머지 값을 결정하였음

2. 제약조건의 연관성
- 화면의 크기의 변화가 있어도 겹쳐지지 말아야할 뷰들은 서로 제약 조건을 갖도록 하였음
- 뷰의 위치는 부모보다는 최대한 가까운 뷰에게 제약조건을 걸고 여백을 통해 지정하였음
- 제약 조건을 주지 않아도 위치가 결정되는 경우가 있었지만 각 뷰의 상대적 위치를 확실하게 하기 위해 가까운 뷰와 제약 조건을 가짐

3. match_parent, wrap_content, match_constraint의 사용
- GridRecyclerView의 item으로 들어갈 layout들은 해당 속성을 사용하기 어려워 길이 값을 하드코딩하였음
- 그 외에는 해당 속성을 모두 사용하였음



> 아래처럼 적으려다가 너무 노가다고 이게 맞나 싶어서 위 처럼 적었는데,, 위는 또 너무 적나? 잘 모르겠다 ,,
> 1. ConstraintLayout을 사용하지 않은 레이아웃
> - CustomView를 위한 레이아웃
> 	- view_card_custom.xml
>	- view_botton_custom.xml
> - ViewPager2의 아이템 레이아웃 - 최상위 레이아웃의 가로세로가 match_parent가 아니면 예외가 발생하여 FrameLayout으로 해결
>	- item_round_info_card.xml
>	- item_card_detail.xml
>	
>2. 모든 경우 가까운 View와 연관지어 여백을 두고 사용
>- Chain
>	- Fragment에 들어갈 뷰들은 위아래가 잘라먹힐 가능성이 있어 모두 체인으로 묶은 뒤 bias 값을 조정하였음
>	- fragment_host_round_setting.xml
>	- fragment_round_meeting_expend.xml
>	- fragment_round_start.xml
>	- fragment_waiting_for_starting_project.xml
>	- 등등
>	
>3. width, height 
	


## (A-2항목) Kotlin collection의 확장함수 사용 / Custom 확장함수 사용📝


## (A-3항목) Library & Purpose📖

**Glide 이미지 로딩 라이브러리**

     implementation "com.github.bumptech.glide:glide:4.10.0"
     kapt "com.github.bumptech.glide:compiler:4.10.0"

**Material디자인 라이브러리(NavigationView 디자인 사용)**

    implementation 'com.google.android.material:material:1.0.0'
			
## (A-3항목) Project Structure📐 


## (A-3항목) Core Function & How to build💻 

**🚪로그인 Kakao api, Google api 사용** 

**🎨Drawing기능 구현**

**💾Drawing 파일처리 및 저장**

**📶socket 통신**

**🎥Animation 적용**

**📢Notification**

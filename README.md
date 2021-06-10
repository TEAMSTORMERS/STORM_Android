
# STORM Android

<img src = "https://user-images.githubusercontent.com/55133871/87793056-b0277880-c87f-11ea-9f5a-62bcb3054a31.gif" width = "100%">
<img src = "https://user-images.githubusercontent.com/56873136/87791082-a7817300-c87c-11ea-919b-9d77ccdaf75e.png" width = "70%">

[![Release](https://img.shields.io/endpoint?color=green&logo=google-play&logoColor=green&url=https%3A%2F%2Fplayshields.herokuapp.com%2Fplay%3Fi%3Dcom.stormers.storm%26l%3DPlayStore%26m%3D%24version)](https://play.google.com/store/apps/details?id=com.stormers.storm)

실시간 브레인스토밍 협업 플랫폼  
STORM은 효율적인 아이디어 회의를 돕는 온라인 툴이자 브레인스토밍을 함께하는 서비스입니다 라운드마다 목표와 제한 시간을 설정해 각자 아이디어를 고민해보고, 각 라운드 후에는 팀원들이 함께 의견을 나눌 수 있습니다. 프로젝트가 끝난 뒤에는 최종 정리를 통해 라운드 및 카드 목록을 한눈에 볼 수 있으며, 좋은 아이디어 카드들은 따로 스크랩해 모아볼 수 있습니다.

---

# 리팩토링 계획
상용 서비스로서 충분한 기능을 가지고 있지만 부족한 실력과 조급한 개발로 인해 불안정하고 유지보수가 거의 불가능한 코드로 작성되어있다. 약 1년만에 프로젝트를 전반적으로 리팩토링하여 안정적이고 추가 기능 확장이 가능한 형태로 만들고자 한다.

참여 인원 : 김성규, 손평화  
기간 : 21.06 ~ (미정)

## Team Rule

1. [Git flow](https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html) 를 지켜 개발한다.
1. Task 단위로 리팩토링을 진행한다. (Login, Main, MyPage, Round, History ...)
1. Pull Request는 다음 내용을 포함한다.
    - 해당 브랜치에서 작업한 것들 나열 및 설명
    - 다른 기능에서 사용가능한 클래스를 개발한 경우, 사용방법을 자세히 설명
    - 다른 기능에서 완성해주어야 할 기능이 있다면 명확하게 명시
    - Reviewer의 의견을 묻고 싶은 부분이 있다면 작성
1. develop 브랜치로의 Merge는 반드시 1명 이상의 Reviewer의 Approve를 받아야 가능하다.
1. Reviewer는 Pull Request를 정독하고 피드백을 상세하게 남긴다.
1. Pull Request 코멘트로 의견을 주고받기에 내용이 많고 연속되는 경우, Slack의 #android_refactor 채널을 사용한다.
1. <b>해당 브랜치의 목적에 맞는 개발만 한다.</b> 
1. 해당 브랜치의 목적에 맞지 않지만 테스트를 위해 수정이 불가피하다면 수정하되, 커밋에 포함하지 않는다.
1. 다른 브랜치에서 완성해야할 기능이 있다면 Todo 주석을 남긴다.
1. develop 브랜치의 최신 상태는 반드시 정상적으로 어플리케이션을 실행가능한 상태여야한다.
1. PR 시 develop 브랜치에 수정 사항이 있거나, develop 브랜치에 추가된 기능이 필요하다면 rebase를 사용한다.

## 패키지 구조

ex) 로그인 기능을 리팩토링하면서 아래와 같은 패키지구조로 클래스 파일들을 생성하였다.
![image](https://user-images.githubusercontent.com/57310034/121476815-140be000-ca02-11eb-8cf3-1782586b655d.png)
- controller 패키지 : 해당 기능을 처리하기 위한 Controller 클래스들을 모아둔다.
    - Controller 클래스란? : ViewModel에서 사용한다. 클라이언트가 특정 기능을 요청하기 위해 사용하는 클래스로, 기능을 직접 처리하지 않는다. 기능을 처리할 수 있는 클래스들을 적절히 선택해서 동작을 위임하는 역할을 함
- model 패키지 : 해당 기능을 처리할 때 사용하는 모델 클래스들을 모아둔다. API 요청 및 응답 객체도 우선은 해당 패키지에 두고, 추후에 클라이언트에서 사용하고 비즈니스 로직을 포함하는 모델 클래스들과 구분이 어려워지면 분리할 예정
- service 패키지 : Retrofit을 사용한 API 요청 메서드를 정의하는 인터페이스를 모아둔다.
- XXXFragment : 해당 기능에 사용되는 Fragment이다. 둘 이상의 Fragment가 사용된다면 fragment 패키지로 묶는다.
- XXXModule : Hilt의 provide 메서드들을 모아두는 Module 클래스이다. 
- XXXViewModel : 해당 기능에 사용될 ViewModel이다. 둘 이상의 ViewModel이 사용된다면 viewmodel 패키지로 묶는다.

![무제](https://user-images.githubusercontent.com/57310034/121480194-c72a0880-ca05-11eb-96e2-d8164e8f9e73.png)

위와 같은 구조를 지키면 기능마다 별도의 package를 가질 수 있기 때문에 협업에 효과적(conflict 해소)

## 코딩 컨벤션
- 기본적으로 [캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)를 따른다. (Kotlin 형태로 유동적으로 변경)
- 변수명은 약어를 사용하지 않는다.
    > ex)   
    > settingBtn(X) → settingButton(O)  
    > ll(X) → linearLayout(O)  
    > tv_card(X) → textview_card(O)  
<br>

- resource naming은 다음 규칙을 따른다.
    <img src="https://user-images.githubusercontent.com/37680108/89560970-d030d300-d852-11ea-8060-585e7632469e.png" width="600">

## 커밋 메시지 컨벤션
```
type: subject

body

footer
```
<br>

### 1) Commit Type
|Type|의미|
|--|--|
|feat | 기능 (새로운 기능)|
|fix | 버그 (버그 수정)|
|refactor | 리팩토링 (네이밍 변경 포함)|
|style | 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)|
|docs | 문서 (문서 추가, 수정, 삭제)|
|test | 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)|
|chore | 기타 변경사항 (빌드 스크립트, 패키지 매니저 수정 등)|
> cf) View에 대한 수정 -> **style**

### 2) Subject

제목은 명령어로 작성한다. 마침표를 붙이지 않는다. 한글 및 영어 모두 사용 가능하다.

### 3) Body (선택)

부연 설명이나 커밋의 이유를 작성한다. 

### 4) Footer (선택)

주로 이슈번호를 남긴다.

> ex) Issue: #00, Resolves: #00, See Also: #00, etc

### 5) References

- [Git - 커밋 메시지 컨벤션](https://doublesprogramming.tistory.com/256)
- [좋은 커밋 메시지를 작성하기 위한 커밋 템플릿 만들어보기](https://junwoo45.github.io/2020-02-06-commit_template/)

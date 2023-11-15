<div align="center">
<img src="https://github.com/2023-finale/.github/assets/101235186/8ac52731-e8aa-4a0d-b1d3-179242249076"/>
<h2>
	캐릭터🤣와 함께하는 TODO 기반 자기관리 어플리케이션☀️
</h2>

<p>
🛁 샤워, 요리 등과 같은 생존을 위한 활동 <br>
📕 성장을 위한 모든 활동 <br>
친분을 다지는 활동 👨‍👨‍👧‍👧<br>
🦾 몸을 건강하게 하는 활동<br>
🎨 당신의 상상력을 펼칠 수 있는 활동<br>
애정하는 대상과의 활동 🐶<br>
<br>
🎧모두 늘해랑과 함께하세요🌞
</p>
<p>
<a href="https://play.google.com/store/apps/details?id=com.finale.neulhaerang"> <img src="https://img.shields.io/badge/Notion%20 보러가기-000000?style=flat-square&logo=notion&logoColor=white" /></a>
<a href="https://play.google.com/store/apps/details?id=com.finale.neulhaerang"><img src="https://img.shields.io/badge/다운로드%20하러가기-3DDC84?style=flat-square&logo=android&logoColor=white" /></a>
</p>

[프로젝트 소개](#🌞-프로젝트-소개) • [주요 기능](#☁-기능-소개)• [프로젝트 차별점](#🔧-기술-스택) • [기술 스택](#🔧-기술-스택)• [인프라 구조](#⚙️-인프라-구조)• [개발 일지](#📚-팀-ashe-개발-일지)• [팀원 소개](#👩‍💻-팀원-소개)

</div>
 
## 🌞 프로젝트 소개

> TODO와 루틴 기반의 자기관리 어플리케이션입니다.\
> Gamification 요소를 통해 사용자의 흥미를 유발하도록 구현하였습니다.\
> 사용자는 TODO와 루틴을 완료하여 설정한 스탯(갓생력, 창의력, 인싸력, 최애력, 튼튼력, 생존력)을 올릴 수 있습니다.\
> TODO와 루틴의 미완료 비율에 따라 나태도가 상승하고 일정 수준 이상이 되면 AR통해 나태괴물을 물리쳐야 계속 어플을 이용할 수 있습니다.\
> 이를 통해 사용자가 캐릭터를 어플 세계의 또 다른 나로 인식할 수 있고 흥미 유발을 할 수 있습니다.


## ☁ 기능 소개
<div align="center">
 <table>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">로그인 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">로그인 후 자동으로 메인 화면으로 접속합니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">메인 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">아이디가 유효할 경우 해당 회원의 이메일로 인증코드를 보내<br/> 비밀번호를 재설정 할 수 있습니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">TODO/루틴 등록 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">이메일 인증, ID/PW 유효성 검사 등을 통해 회원가입을 합니다.</td>
    </tr> 
   <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">마이페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">회원가입, 비밀번호 찾기를 하면 등록한 메일로 인증번호가 전달됩니다.</td>
    </tr> 
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">나태괴물 처치</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">공지사항과 지역은 Swiper로 자동 슬라이드 되며, <br/>이용 방법 옆 물음표를 누를 시 사이트의 이용 방법이 나옵니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">소셜(주위사용자 확인)</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">여행 제목과 여행 기간을 입력받습니다.<br>
초대하기에서 유저의 닉네임으로 검색이 가능합니다.<br>
'+' 버튼을 통해 여행에 초대할 수 있습니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">여행 계획 세우기 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">찜한 장소 목록에서 드로그 앤 드랍으로 장소를 넣을 수 있습니다.<br>
또는 검색 탭에서 장소를 검색하여 원하는 일자에 넣을 수 있습니다.<br>
날짜 추가 버튼과 날짜 삭제 버튼을 통해 자유롭게 일정을 조정할 수 있습니다.<br>
하나의 일정의 MORE 버튼으로 해당 장소의 상세 정보를 볼 수 있고 여행지별 메모를 남길수 있습니다.<br>
Day에 일정이 추가되면 각 여행지 간의 거리가 보이고 소요 시간이 나타납니다.<br>
연결 색을 다르게 하여 쉽게 구분할 수 있습니다.<br>
여행 계획이 완료되었다면 계획 완료 버튼을 통해 계획을 저장합니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">여행 계획 완성 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">지도에 여행 일정동안 방문하는 곳이 나타납니다.<br>
지도 아래로 방문 지정한 방문 시간이 나타납니다.<br>
'+' 버튼을 누르면 해당 장소의 상세 정보가 나타납니다.<br>
태그 표시가 있는 곳은 메모가 있는 장소입니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">여행 계획 공유</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">카카오톡 공유하기로 내가 만든 여행 계획에 친구를 초대할 수 있습니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">우편함</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
        <tr>
      <td align="center">
첫번째 탭에서 시/도 정보를 선택합니다.<br>
그에 따른 상세 지역을 선택합니다.<br>
여행지 분류를 선택합니다.<br>
Search에서 여행지 명을 검색할 수 있습니다.<br>
검색 버튼을 누르면 해당 되는 여행지 정보가 리스트로 나타납니다.<br>
지도에 마커와 해당 위치 정보를 나타내는 네이버 지도 페이지로 이동합니다.<br>
여행지를 누르면 모달로 상세 정보가 주어집니다.<br>
자동차 버튼을 누르면 나만의 장소에 추가되고 상단 우측에 개수가 표시됩니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">마이페이지(의상실)</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
      <td align="center">여행 검색 페이지에서 나만의 장소에 등록한 장소들이 리스트 형식으로 나타납니다.</td>
    </tr>
    <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">마이페이지(캘린더)</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">캘린더에서 자신이 속한 모든 여행이 나타납니다.<br>날짜를 클릭하면 해당 날짜가 속한 여행이 리스트 형식으로 나타납니다.</td>
    </tr>
   <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">핫플레이스 리스트</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">우리나라의 각 시 별로 회원들이 공유한 핫 플레이스 장소를 확인할 수 있습니다.</td>
    </tr>
   <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">핫플레이스 등록하기</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">핫 플레이스로 등록할 장소를 검색하면 해당 위치가 왼쪽 지도에 표시됩니다. <br/>
      제목과 내용을 입력한 후 이미지 1개를 업로드 하면 핫 플레이스로 게시물이 등록됩니다. <br/>
      회원들은 게시물에 좋아요와 댓글을 남길 수 있습니다. </td>
    </tr>
   <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">공지사항 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">관리자의 공지사항과 회원들의 QnA를 확인할 수 있습니다. <br/> 
      카테고리에 따른 검색이 가능하며 카테고리를 클릭할 시 게시글이 정렬됩니다. </td>
    </tr>
   <tr>
      <td align="center" style="font-weight: bold; font-size: 18;">공지사항 상세 페이지</td>
    </tr>
    <tr>
      <td align="center"><img src="" /></td>
    </tr>
    <tr>
      <td align="center">각 게시물에는 댓글을 남길 수 있으며 비밀댓글(댓글 작성자, 게시글 작성자만 확인 가능)도 가능합니다. <br/> 
      댓글은 게시글 작성자와 댓글을 작성한 본인만 삭제 가능합니다.</td>
    </tr>
 </table>
</div>

## ⛅ 프로젝트 차별점 및 기술 특이점

- `캐릭터를 육성하는 게임적인 요소로 흥미유발`
	- TODO와 루틴을 완료하면 캐릭터의 6가지 스탯중 한가지를 올릴 수 있음
	- 스탯은 갓생력, 인싸력, 최애력, 생존력, 튼튼력, 창의력
	- TODO는 2 스탯 포인트, 루틴은 5 스탯 포인트를 올릴 수 있음
	- 사용자는 앱을 사용하며 아이템, 칭호 등의 보상을 얻고 캐릭터를 커스터마이징 할 수 있음
	- 이벤트 함수 로직을 통해 일정 조건을 달성하면 사용자가 칭호를 얻음
- `AR을 활용하여 게임적인 요소를 추가`
	- Uaal(Unity as a Library)를 도입하여 안드로인드 어플에서 Unity의 AR 기능을 이식
	- 나태도가 70이상이면 나태괴물을 물리쳐야만 TODO와 루틴 수행 가능
	- 나태도가 50이상일 때부터 나태괴물 처치 가능
	- AR을 활용하여 3마리의 나태괴물을 물리치면 나태도 50 하락
- `수면데이터를 사용하여 현실감 증가`
	- 삼성 헬스와 헬스 커넥트를 이용하여 건강 데이터 수집
	- 수면량에 따라 생성할 수 있는 TODO와 루틴의 개수를 제한
	- 피곤한 사용자가 무리하지 않도록 구현
- `AI를 활용한 편지 제공`
	- 사용자가 전날 완료한 TODO와 루틴을 기반으로 편지를 제공
	- 이를 통해 사용자의 지속적인 사용을 독려하고 긍정적인 요소를 불어넣음
- `주위 사용자 찾기를 통한 소셜 기능`
	- Redis Geospatial을 이용하여 기기의 GPS 데이터로 주변 100m 이내의 사용자들의 정보를 실시간으로 가져옴
	- 주변 사용자의 커스터마이징된 캐릭터 정보를 확인할 수 있음

## 🔧 기술 스택


**Backend**

![Java](https://img.shields.io/badge/Java%2011-yellow.svg?&style=for-the-badge&logo=java&logoColor=#3776AB)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%202.7.17-6DB33F.svg?&style=for-the-badge&logo=Spring%20Boot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle%208.3-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?&style=for-the-badge&logo=Spring%20Security&logoColor=white)
![JPA](https://img.shields.io/badge/Spring%20data%20JPA-85EA2D?style=for-the-badge&logo=JPA&logoColor=white)
![JWT](https://img.shields.io/badge/JWT%200.9.1-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok%201.18.30-FF5722?style=for-the-badge&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit%204.13.1-grey?style=for-the-badge&logoColor=white&logo=junit4)

**Frontend**

![Kotlin](https://img.shields.io/badge/Kotlin%201.8-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![jetpack compose](https://img.shields.io/badge/jetpack%20compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![compose ui](https://img.shields.io/badge/compose%20ui%201.5.4-4285F4?style=for-the-badge&logoColor=white&logo=jetpackcompose)
![compose material3](https://img.shields.io/badge/compose%20material%201.1.2-757575?style=for-the-badge&logoColor=white&logo=materialdesign)
![retrofit2](https://img.shields.io/badge/retrofit%202.9.0-48b983?style=for-the-badge&logoColor=white)

**Database**

![Redis](https://img.shields.io/badge/Redis%207.2.3-DC382D.svg?&style=for-the-badge&logo=Redis&logoColor=white)
![MongoDB](https://img.shields.io/badge/mongoDB%207.0.2-47A248?style=for-the-badge&logo=MongoDB&logoColor=white)
![MariaDB](https://img.shields.io/badge/mariaDB%2011.1.2-003545?style=for-the-badge&logo=mariaDB&logoColor=white)

**Management Tool**

![Notion](https://img.shields.io/badge/Notion-white.svg?&style=for-the-badge&logo=Notion&logoColor=black)
![Mattermost](https://img.shields.io/badge/Mattermost-040281.svg?&style=for-the-badge&logo=Mattermost)
![Gitlab](https://img.shields.io/badge/Gitlab-FC6D26.svg?&style=for-the-badge&logo=Gitlab&logoColor=white)
![JIRA](https://img.shields.io/badge/jira-2956F5.svg?&style=for-the-badge&logo=Jira%20Software&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-black.svg?&style=for-the-badge&logo=Figma&logoColor=white)

**CI/CD**

![Ubuntu](https://img.shields.io/badge/Ubuntu%2020.04-E95420.svg?&style=for-the-badge&logo=Ubuntu&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939.svg?&style=for-the-badge&logo=Jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED.svg?&style=for-the-badge&logo=Docker&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639.svg?&style=for-the-badge&logo=Nginx&logoColor=white)
![EC2](https://img.shields.io/badge/amazon%20ec2-FF9900.svg?&style=for-the-badge&logo=amazonec2&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624.svg?&style=for-the-badge&logo=Linux&logoColor=white)


**IDE**

![Intellij](https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Adroid studio](https://img.shields.io/badge/android%20studio-3DDC84?style=for-the-badge&logo=androidauto&logoColor=white)
![visual studio](https://img.shields.io/badge/visual%20studio-5C2D91?style=for-the-badge&logo=visualstudio&logoColor=white)
![unity](https://img.shields.io/badge/unity%202022.3.12f1-000000?style=for-the-badge&logo=unity&logoColor=white)

**3rd party**

![Firebase](https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white)
![Kakao Login](https://img.shields.io/badge/Kakao%20Login-FFCD00?style=for-the-badge&logo=KakaoTalk&logoColor=white)
![Chat GPT](https://img.shields.io/badge/Ghat%20GPT-00a495?style=for-the-badge&logoColor=white)

## 🎇 시스템 아키텍처

<img src="https://user-images.githubusercontent.com/33210124/194321053-3df8a037-3e79-4702-8469-fdffaf657527.png" alt="시스템 아키텍처" width="80%">


## 📂 기획 및 설계 산출물

### [💭 요구사항 정의 및 기능 명세](https://lab.ssafy.com/s09-final/S09P31A502/-/blob/develop/exec/%EA%B8%B0%EB%8A%A5%EB%AA%85%EC%84%B8%EC%84%9C.pdf?ref_type=heads)

### [🎨 화면 설계서](https://www.figma.com/file/WMeAanpQhVT7a05kSzpagk/A502?type=design&node-id=485-165&mode=design&t=J3HZlBmWB3l69f7y-0)

![화면설계서](https://github.com/2023-finale/.github/assets/101235186/803ea51b-acf8-4a8b-a308-e63267e4e954)


### [🐾 API 명세서](https://lab.ssafy.com/s09-final/S09P31A502/-/blob/develop/exec/API%20%EB%AA%85%EC%84%B8%EC%84%9C.pdf?ref_type=heads)

### ✨ ER Diagram

![늘해랑 ERD](https://github.com/2023-finale/.github/assets/101235186/a3afa78d-f914-43ce-8663-9b38bf8dbafc)

### [🐛 버그 리포트](https://young-bonsai-e9c.notion.site/efbc4c1a09ff494c881d0ad2c8b712d0?pvs=4)

## 🤙🏻 Conventions
FINALE 팀원들의 원활한 소통을 위한 [✨MR 컨벤션](https://young-bonsai-e9c.notion.site/MR-PR-48eeb2cb70d2449fb4611b5b452c479b?pvs=4),[ Commit 컨벤션](https://young-bonsai-e9c.notion.site/Git-Commit-b454384df80e4894bd3b879a164522e3?pvs=4), [ JAVA 컨벤션](https://young-bonsai-e9c.notion.site/Java-c4b5d639436943eab0b37e65afe4f6ac?pvs=4), [ Kotlin 컨벤션✨](https://young-bonsai-e9c.notion.site/Kotlin-FE-2936c2479dbf4d649ec24bfffd5c10a8?pvs=4)  입니다 😎



## 💗 팀원 소개
##### 📣 늘해랑을 만든 `FINALE` 팀원들을 소개합니다!

![팀원소개](https://github.com/2023-finale/.github/assets/101235186/d065cdf5-24fc-4e04-96dd-a6c408f9b728)


|**[이희정](https://github.com/heejeong13)**|**[강준영](https://github.com/Judgement9882)**|**[김민태](https://github.com/mi2ntae)**|**[김유진](https://github.com/Yujin830)**|**[박정은](https://github.com/jungeunevepark)**|**[임성원](https://github.com/imsw0529)** |
| :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/18045556?v=4" width="800"> | <img src="https://avatars.githubusercontent.com/u/79979086?v=4" width="800"> | <img src="https://avatars.githubusercontent.com/u/73164347?v=4" width="800"> | <img src="https://avatars.githubusercontent.com/u/96433955?v=4" width="800"> | <img src="https://avatars.githubusercontent.com/u/101235186?v=4" width="800"> | <img src="https://avatars.githubusercontent.com/u/37894963?v=4" width="800"> |
|Leader & Frontend|Frontend|Backend & Infra|Frontend & Backend|Backend|Frontend|

## 💌 팀원 역할

- **이희정**
  - 팀장
- **강준영**
  - 마이페이지 구현, 캐릭터 커스터마이징 기능 구현
- **김민태**
  - 소셜 로그인 및 회원관리, 주위 사용자 확인 기능, CI/CD 구축
- **김유진**
  - TODO API 구현, 나태괴물 처치 기능 구현
- **박정은**
  - 루틴 API 구현, 칭호 이벤트 로직 구현, 스케줄러 로직 구현
- **임성원**
  - TODO 입력, 수정 페이지
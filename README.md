# 🐥 TripBuddy

![img](resources/readme-img/main1.gif)

**여행 계획(플랜)을 생성하고, 친구들과 공유해 함께 편집할 수 있는 웹 서비스**

| 초기 화면                              | 메인 화면                              | 실시간 통신 화면                       |
| -------------------------------------- | -------------------------------------- | -------------------------------------- |
| ![img](resources/readme-img/main1.gif) | ![img](resources/readme-img/main3.gif) | ![img](resources/readme-img/main2.gif) |

---

## 시연 영상

[![Video Label](http://img.youtube.com/vi/eVXOb6TZwgk/0.jpg)](https://youtu.be/eVXOb6TZwgk)

- 타임라인 (클릭시 유튜브로 이동)
  - [회원가입 및 로그인](https://youtu.be/eVXOb6TZwgk?t=12)
  - [메인화면](https://youtu.be/eVXOb6TZwgk?t=28)
  - [공지사항](https://youtu.be/eVXOb6TZwgk?t=38)
  - [여행후기](https://youtu.be/eVXOb6TZwgk?t=55)
  - [마이페이지](https://youtu.be/eVXOb6TZwgk?t=85)
  - [알림기능](https://youtu.be/eVXOb6TZwgk?t=103)
  - [플랜수정 - 관광지 검색 및 경로 추가](https://youtu.be/eVXOb6TZwgk?t=130)
  - [플랜수정 - 관광지 찜 기능](https://youtu.be/eVXOb6TZwgk?t=166)
  - [플랜수정 - 관광지에 메모 등록](https://youtu.be/eVXOb6TZwgk?t=200)
  - [WebSocket 기반 실시간 통신 - 채팅](https://youtu.be/eVXOb6TZwgk?t=210)
  - [WebSocket 기반 실시간 통신 - 코스 변경](https://youtu.be/eVXOb6TZwgk?t=236)
  - [여행후기 작성](https://youtu.be/eVXOb6TZwgk?t=260)
  - [개인페이지 - 트립 로그](https://youtu.be/eVXOb6TZwgk?t=273)

---

# :computer: 담당한 역할

- Stateless
  - SpringBoot 초기 세팅, Controller, Service, Mapper, DTO 클래스 설계
  - Plan, Review, Users, Follow, Wish, Comment, Notify 컨트롤러에 대한 **API 및 MyBatis 쿼리 작성**
  - **JWT Token** Service 작성
  - **Filter**를 사용한 일부 Request Wrapping 처리
  - **Interceptor**를 통한 API 별 인가 처리
  - **SMTP 기반 메일 전송**을 통한 비밀번호 초기화 기능
- Stateful
  - WebSocket 기반 **연결 관리를 위한 NetworkService** 작성
    - `ConcurrentHashMap`을 사용한 세션 관리, 플랜 별 Room 개념 도입
    - Room 별 여행 경로 상태를 접속 유저와 공유하여 동기화
  - **소켓 핸들러** 작성, **패킷 프로시져 로직** 작성

---

# :seedling: 기술 스택

- SpringBoot + Maven
- MyBatis
- MySQL
- Swagger
- WebSocket
- Git

---

# :chart_with_upwards_trend: ERD

![img](resources/readme-img/design/ERD.png)

---

# :chart_with_upwards_trend: 클래스 다이어그램

![img](resources/readme-img/design/ClassDiagram.png)

---

# :bookmark: 주요 컨트롤러

| 컨트롤러 이름   | 경로      | 설명                                      |
| --------------- | --------- | ----------------------------------------- |
| 유저 컨트롤러   | /users    | 회원 정보 관리 및 로그인 처리             |
| 관광지 컨트롤러 | /attrs    | 관광지 정보 및 검색 처리                  |
| 플랜 컨트롤러   | /plans    | 여행 계획 관리                            |
| 메모 컨트롤러   | /memos    | 여행 계획 속 단일 경유지에 대한 메모 처리 |
| 리뷰 컨트롤러   | /reviews  | 리뷰 게시글 처리                          |
| 댓글 컨트롤러   | /comments | 리뷰에 대한 댓글 처리                     |
| 공지 컨트롤러   | /notices  | 공지 게시글 처리                          |
| 알림 컨트롤러   | /notifys  | 개별 사용자에 대한 알림 처리              |
| 팔로우 컨트롤러 | /follows  | 팔로우 관계 처리                          |
| 찜 컨트롤러     | /wishes   | 개별 사용자가 찜한 관광지에 대한 처리     |
| 실시간 채팅     | /chat     | WebSocket 기반 실시간 패킷 처리           |

---

# :page_facing_up: 전체 API 리스트

| 설명                    | 메소드                                                       | 인터셉터                                                                                                                                                                   | URL                               |
| ----------------------- | ------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------- |
| 시도 리스트             | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /attrs/areacodes                  |
| 군구 리스트             | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /attrs/areacodes/{sido}           |
| 관광지 검색             | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /attrs/search?<i>querystring…</i> |
| 관광지 정보             | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /attrs/{contentid}                |
| 메모 등록               | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Plan-aa79d4"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /memos                            |
| 메모 리스트             | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Plan-aa79d4"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /memos/{planid}/{courseid}        |
| 메모 삭제               | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Memo-d66a44"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /memos/{memoid}                   |
| 공지 등록               | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Admin-yellowgreen"> <img src="https://img.shields.io/badge/-Session-orange">                                                       | /notices                          |
| 공지 수정               | <img src="https://img.shields.io/badge/-PUT-orange">         | <img src="https://img.shields.io/badge/-Notice-gray"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /notices                          |
| 공지 리스트             | <img src="https://img.shields.io/badge/-GET-blue">           |                                                                                                                                                                            | /notices?page={page}              |
| 공지 게시글             | <img src="https://img.shields.io/badge/-GET-blue">           |                                                                                                                                                                            | /notices/{noticeid}               |
| 공지 삭제               | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Admin-yellowgreen"> <img src="https://img.shields.io/badge/-Notice-gray"> <img src="https://img.shields.io/badge/-Session-orange"> | /notices/{noticeid}               |
| 초대 알림 전송          | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Plan-aa79d4"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /notifys/invite                   |
| 알림 정보               | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /notifys?page={page}              |
| 알림 내 버튼 삭제       | <img src="https://img.shields.io/badge/-PUT-orange">         | <img src="https://img.shields.io/badge/-Notify-blue"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /notifys                          |
| 썸네일 있는 플랜 리스트 | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /plans/thumbnails                 |
| 플랜 리스트             | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /plans                            |
| 플랜 정보               | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Plan-aa79d4"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /plans/{planid}                   |
| 새 플랜 등록            | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /plans                            |
| 플랜 수정               | <img src="https://img.shields.io/badge/-PUT-orange">         | <img src="https://img.shields.io/badge/-Plan-aa79d4"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /plans                            |
| 플랜 탈퇴               | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Plan-aa79d4"> <img src="https://img.shields.io/badge/-Session-orange">                                                             | /plans/{planid}/users             |
| 플랜 가입               | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /plans/{planid}/users             |
| 플랜 스크랩             | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /plans/{planid}                   |
| 리뷰 작성               | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /reviews                          |
| 리뷰 리스트             | <img src="https://img.shields.io/badge/-GET-blue">           |                                                                                                                                                                            | /reviews?page={page}&sort={sort}  |
| 리뷰 게시글             | <img src="https://img.shields.io/badge/-GET-blue">           |                                                                                                                                                                            | /reviews/{reviewid}               |
| 인기 리뷰 리스트        | <img src="https://img.shields.io/badge/-GET-blue">           |                                                                                                                                                                            | /reviews/hot                      |
| 리뷰 댓글 리스트        | <img src="https://img.shields.io/badge/-GET-blue">           |                                                                                                                                                                            | /reviews/{reviewid}/comments      |
| 사용자 리뷰 리스트      | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /users/{userid}/reviews           |
| 리뷰 수정               | <img src="https://img.shields.io/badge/-PUT-orange">         | <img src="https://img.shields.io/badge/-Review-eb6fa6"> <img src="https://img.shields.io/badge/-Session-orange">                                                           | /reviews                          |
| 리뷰 삭제               | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Review-eb6fa6"> <img src="https://img.shields.io/badge/-Session-orange">                                                           | /reviews/{reviewid}               |
| 리뷰 평점               | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Review-eb6fa6"> <img src="https://img.shields.io/badge/-Session-orange">                                                           | /reviews/rating                   |
| 유저 로그인             | <img src="https://img.shields.io/badge/-POST-red">           |                                                                                                                                                                            | /users/login                      |
| 유저 로그아웃           | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /users/logout                     |
| 유저 등록               | <img src="https://img.shields.io/badge/-POST-red">           |                                                                                                                                                                            | /users                            |
| 유저 탈퇴               | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /users                            |
| 유저 정보               | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /users/{userid}                   |
| 유저 정보 수정          | <img src="https://img.shields.io/badge/-PUT-orange">         | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /users                            |
| 유저 비밀번호 초기화    | <img src="https://img.shields.io/badge/-POST-red">           |                                                                                                                                                                            | /users/email                      |
| 유저 팔로우             | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /follow                           |
| 유저 언팔로우           | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /follow/{followingid}             |
| 팔로잉 리스트           | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /follow/followees/{followerid}    |
| 팔로워 리스트           | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /follow/followers/{followeeid}    |
| 관광지 찜               | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /wishes                           |
| 관광지 찜 취소          | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /wishes/{contentid}               |
| 찜한 관광지 리스트      | <img src="https://img.shields.io/badge/-GET-blue">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /wishes?page={page}               |
| 리뷰 댓글 작성          | <img src="https://img.shields.io/badge/-POST-red">           | <img src="https://img.shields.io/badge/-Session-orange">                                                                                                                   | /comments                         |
| 리뷰 댓글 수정          | <img src="https://img.shields.io/badge/-PUT-orange">         | <img src="https://img.shields.io/badge/-Comment-d6a689"> <img src="https://img.shields.io/badge/-Session-orange">                                                          | /comments                         |
| 리뷰 댓글 삭제          | <img src="https://img.shields.io/badge/-DELETE-yellowgreen"> | <img src="https://img.shields.io/badge/-Comment-d6a689"> <img src="https://img.shields.io/badge/-Session-orange">                                                          | /comments/{commentid}             |

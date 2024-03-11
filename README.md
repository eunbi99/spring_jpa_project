# 👩‍💻 Spring Project
### 🛠 사용 기술
- Java 17
- Spring Boot 3.2.2
- Spring Data JPA
- QueryDSL
- MySQL
- Spring Security
- JWT
- Redis
- Swagger
- Gradle

### 🗺 프로젝트 소개 및 구현 기능
---
유럽여행의 동행을 구할 수 있는 웹사이트입니다.
#### 👥 유저 (회원가입 및 로그인)
  - Spring Security JWT를 사용하여 회원가입과 로그인을 할 수 있습니다.
  - ROLE은 USER와 ADMIN으로 구분하여 회원가입을 할 수 있습니다.
  - 로그인 성공 시 Access token과 Refresh token을 발급할 수 있습니다.
  - Redis를 사용해 refresh token을 저장할 수 있습니다.
  - Access token이 만료되었을 경우, Redis에 저장된 refresh token으로 검증 후 재발급 받을 수 있습니다.
#### 💡 카테고리
- 무한뎁스 카테고리를 구현을 통해 상위카테고리인 나라와 하위 카테고리인 도시를 등록할 수 있습니다.
#### 📝 게시글 
- 게시글을 등록할 때 등록된 카테고리를 선택하고, 제목,내용,성별,모집인원 수 등의 내용의 게시글 등록/상세조회/수정/삭제를 할 수 있습니다.
- JPA + QueryDsl의 동적 쿼리를 사용하여 검색 조건에 따라 게시글을 조회할 수 있습니다. 
- 커서 기반 방식의 페이징을 통해 6개씩 페이징 해서 조회합니다.
#### ❤️ 좋아요
- 유저는 게시글에 좋아요를 누를 수 있습니다.
#### 📄문서화
- Swagger를 통해 API 문서를 자동으로 생성하고 관리할 수 있습니다.


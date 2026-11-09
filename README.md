# 🎬 Movie REST API (Spring Data JPA & MariaDB)

## ① 프로젝트 소개

* **주제:** Spring Data JPA와 MariaDB를 활용한 영화(Movie) 정보 관리 RESTful CRUD 웹 애플리케이션
* **기능:** POST, GET, PUT, DELETE, Render 배포, MariaDB 연동
* **프로젝트 구조:** Controller → Service → JpaRepository → Hibernate → MariaDB
* **Entity 필드 구조 (`Movie`):**
  * id, title, director, genre, runtime, rating, releaseYear

* **Repository 및 배포 URL:**
  * **GitHub Organization Repository:** https://github.com/2026-2-WebService/assign09-c01-22200439
  * **Personal GitHub Repository:** https://github.com/mingee03/week09
  * **Render Deployment URL:** https://week09-1-znml.onrender.com/

## ② Database SQL Practice

### 새 Table 생성 및 SQL CRUD 실습 (STEP 2: `movie_sql`)
* **실행 SQL:**
```sql
1) Table 생성
CREATE TABLE movie_sql (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    director VARCHAR(100),
    genre VARCHAR(50),
    rating DOUBLE
);

2) 데이터 추가
INSERT INTO movie_sql(title, director, genre, rating)
VALUES 
('Inception', 'Nolan', 'SF', 9.1), 
('Interstellar', 'Nolan', 'SF', 9.3), 
('Parasite', 'Bong', 'Drama', 9.0);

3) 데이터 수정
UPDATE movie_sql SET rating = 9.5 WHERE title = 'Interstellar';

4) 데이터 삭제
DELETE FROM movie_sql WHERE title = 'Parasite';

5) 조건 조회
SELECT * FROM movie_sql;
SELECT title, rating FROM movie_sql;
SELECT * FROM movie_sql WHERE genre = 'SF';
```
* **실행 결과 화면:**

SELECT * FROM movie_sql; 전체 조회 결과 화면

<img width="787" height="481" alt="image" src="https://github.com/user-attachments/assets/d4c7177b-0bb8-4f6b-987a-0a9ec0b25217" />


SELECT * FROM movie_sql WHERE genre = 'SF'; 조건 조회 결과 화면

<img width="827" height="505" alt="image" src="https://github.com/user-attachments/assets/4645ae0b-adf8-49bd-a4e3-406819450f90" />

---

## ③ Spring Data JPA 구현

### 1. Entity 및 JpaRepository
* **`Movie` Entity:** Database의 `movies` 테이블과 1:1로 매핑되는 도메인 클래스입니다. `id`를 제외한 6개의 필드를 가집니다.
* **`MovieRepository`:** Spring Data JPA의 `JpaRepository<Movie, Long>` 인터페이스를 상속받아 SQL 작성 없이 CRUD 기능을 사용 가능합니다.

### 2. 사용한 어노테이션의 역할
* `@Entity`: 데이터베이스 엔티티 객체임을 선언합니다.
* `@Table(name = "movies")`: 엔티티가 매핑될 데이터베이스의 테이블명을 `movies`로 지정합니다.
* `@Id`: 해당 필드가 테이블의 Primary Key임을 나타냅니다.
* `@GeneratedValue(strategy = GenerationType.IDENTITY)`: 기본키 생성을 데이터베이스에 맡깁니다.
* `@Column`: 테이블의 컬럼 속성을 정의하며, 제약조건을 설정합니다.

### 3. REST API Endpoint 목록

| HTTP Method | URL | 설명 | Request Body | Response Status |
|---|---|---|---|---|
| **POST** | `/api/movies` | 영화 데이터 신규 등록 | `MovieRequest` JSON | `201 Created` |
| **GET** | `/api/movies` | 영화 전체 목록 조회 | 없음 | `200 OK` |
| **GET** | `/api/movies/{id}` | 특정 ID 영화 단건 조회 | 없음 | `200 OK` / `404 Not Found` |
| **PUT** | `/api/movies/{id}` | 특정 ID 영화 정보 수정 | `MovieRequest` JSON | `200 OK` / `404 Not Found` |
| **DELETE**| `/api/movies/{id}` | 특정 ID 영화 정보 삭제 | 없음 | `204 No Content` / `404 Not Found`|

* **요청 JSON 예시 (`MovieRequest`):**
```json
{
  "title": "인셉션",
  "director": "크리스토퍼 놀란",
  "genre": "SF",
  "runtime": 148,
  "rating": 9.2,
  "releaseYear": 2010
}
```
* **응답 JSON 예시 (`MovieResponse`):**
```json
{
  "id": 1,
  "title": "인셉션",
  "director": "크리스토퍼 놀란",
  "genre": "SF",
  "runtime": 148,
  "rating": 9.2,
  "releaseYear": 2010
}
```

---

## ④ JPA와 SQL 비교
### SQL 직접 작성 방식 vs JPA 방식 비교
* **SQL 직접 작성 방식:** 데이터베이스 중심적인 사고로, 테이블 스키마에 종속적인 쿼리문을 개발자가 일일이 작성해야 하며 오타나 문법 오류가 런타임 시점에 발견될 위험이 있습니다.
* **JPA 방식:** 객체지향적인 자바 도메인 모델(`Movie`)을 중심으로 비즈니스 로직을 개발하면, JPA가 내부적으로 알맞은 SQL을 자동 생성하여 실행해 줍니다. 데이터베이스 종류(MariaDB, MySQL, PostgreSQL 등)가 바뀌더라도 방언(Dialect) 설정만 바꾸면 코드 수정 없이 호환되는 큰 장점이 있습니다.

---

## ⑤ 배포 및 환경 설정

### 1. 환경변수 설정
실제 Database 접속 계정과 비밀번호를 코드에 노출하지 않기 위해 환경변수를 사용하였습니다.
* **사용된 환경변수 명칭:**
  * `DB_URL`: JDBC URL
  * `DB_USERNAME`: 계정명
  * `DB_PASSWORD`: 비밀번호
* **`application.properties` 설정:**
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

### 2. Render 배포 과정
* multi-stage 빌드를 적용한 `Dockerfile`을 작성하여 JDK 21 Alpine 환경에서 Gradle 빌드를 수행하고 JRE 환경에서 경량 컨테이너로 실행되도록 구성했습니다.
* Render Web Service 생성 후 환경변수(`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`)를 등록하여 원격 MariaDB 서버(`walab.handong.edu`)와 성공적으로 통신하도록 배포했습니다.

### 3. 배포 URL CRUD 테스트 및 DB 반영 확인

1: Render 대시보드 Live 상태 화면

<img width="1432" height="801" alt="image" src="https://github.com/user-attachments/assets/77ad443b-101e-4f62-8738-9b312fce0ffc" />

2: Postman에서 배포 URL로 POST 등록 성공 화면

<img width="922" height="850" alt="image" src="https://github.com/user-attachments/assets/497648e5-625b-40e5-887c-9325d4dc3cdb" />

3: Postman에서 배포 URL로 GET 전체 조회 결과 화면

<img width="1112" height="877" alt="image" src="https://github.com/user-attachments/assets/f715035f-9daa-4188-be49-c7796721c4d5" />


4: DBeaver에서 SELECT * FROM movies; 로 저장 확인된 결과 표

<img width="1027" height="552" alt="image" src="https://github.com/user-attachments/assets/634fc9c5-0f14-45cd-9591-0399e8312c74" />


---

## ⑥ Weekly Report

### 1. Key Learning (핵심 학습 내용 3가지)
1. JPA의 `JpaRepository`가 추상화하여 자동으로 처리해 주는 과정을 배우며 생산성의 큰 차이를 경험했습니다.
2. 엔티티 클래스만 정의하고 서버를 구동했을 때, Hibernate가 DBeaver 상에 `movies` 테이블을 자동으로 생성하고 필드 타입을 매핑하는 과정을 체감했습니다.
3. 아이디, 비밀번호와 같은 민감 정보를 IntelliJ 실행 설정 및 Render의 환경변수로 분리 주입하는 방식을 배웠습니다.

### 2. Problem & Solution (문제 해결 과정)
* 문제: 스프링 부트 로컬 실행 시 `Could not resolve placeholder 'DB_URL'` 에러가 발생하며 서버 구동이 실패했습니다.
* 원인 및 해결: IntelliJ의 Run Configuration 환경변수가 잘못 넣어져 `DB_URL` 변수가 정상 주입되지 않았던 것이 원인이었습니다. 환경변수 창을 정리하여 등록하여 해결했습니다.

### 3. Code Review (중요 코드 1개와 동작 설명)
* **코드 위치:** `MovieService.java`의 `update` 메서드
```java
public MovieResponse update(Long id, MovieRequest request) {
    Movie movie = findEntity(id);
    movie.update(request);
    return MovieResponse.from(repository.save(movie));
}
```
* **동작 설명:** 
  1. `findEntity(id)`를 호출하여 전달받은 ID의 엔티티가 존재하는지 먼저 확인하고, 없으면 예외를 던집니다.
  2. 조회된 `Movie`의 `update(request)` 비즈니스 메서드를 호출하여 데이터를 안전하게 변경합니다.
  3. `repository.save(movie)`를 통해 데이터베이스에 반영하고, 수정된 엔티티를 DTO로 래핑하여 반환합니다.

### 4. AI Usage (생성형 AI 활용 내역)
* **사용한 AI 도구:** Google DeepMind Antigravity (Gemini 기반 어시스턴트)
* **사용한 부분:** JPA Entity 필드 조건 검토, Hibernate SQL 매핑 분석, Dockerfile 작성 및 README 작성 보조
* **사용한 Prompt:** "JPA Entity id 제외 5개 필드 구성 요건 충족 검토", "Render 배포용 Java 21 Dockerfile 작성"
* **직접 수정 및 검증한 내용:** AI가 제시한 코드 구조를 기반으로 직접 패키지별로 분할 커밋을 진행하였고, 배포 URL을 발급받아 실제 Postman 테스트와 DBeaver 테이블 조회를 직접 수행하여 데이터를 검증했습니다.
* **자신이 생각하는 AI 사용률:** **35%** (설계 가이드 및 오류 분석에 보조적으로 활용하고, 코드 실행 및 Git/배포 작업은 직접 수행)

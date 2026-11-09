package org.example.week09.repository;

import org.example.week09.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

// Q3. JpaRepository<Entity, Long>에서 두 타입은 각각 무엇을 의미하는가?
//Movie는 리포지토리가 다룰 JPA의 객체, Long은 해당 객체의 PK이다.
// Q4. JpaRepository에서 기본 제공하는 CRUD 메서드를 5개 이상 찾아 메서드명과 역할을 주석으로 작성하세요.
// 1. save() = entity를 데이터베이스에 신규등록하거나 수정
// 2. findById(): id에 해당하는 데이터 단건 조회
// 3. findAll(): 모든 데이터 리스트로 반환
// 4. deleteById(): id에 해당하는 데이터 삭제
// 5. existsById(): id에 해당하는 데이터가 DB에 존재하는 지 확인 후 boolean으로 반환
// Q5. findById()의 반환 타입이 Optional인 이유는 무엇인가?
// 찾으려는 데이터가 존재하지 않으면 null이 될 수 있기 때문에, orElseThrow 등으로 감싸기 위해 사용
// Q6. JpaRepository에서 기본 제공하는 메서드와 개발자가 직접 작성하는 Query Method의 차이는 무엇인가?
// 기본 제공 메소드는 단순 표준적이고, 직접 작성하는 메서드는 목적에 따라 복잡한 조회가 가능하다.
public interface MovieRepository extends JpaRepository<Movie, Long> {
}

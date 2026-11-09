package org.example.week09.entity;
import jakarta.persistence.EntityListeners;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import org.example.week09.dto.MovieRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Q1. @Entity, @Id, @GeneratedValue는 각각 어떤 역할을 하는가? (@Entity)
/*@Entity는 JPA가 관리할 객체임을 선언, @Id는 해달 필드가 객체의 PK임을 선언, @GeneratedValue는 PK 생성 방식을 설정*/
@Entity
// Q2. Entity 클래스와 데이터베이스 테이블은 어떻게 연결되는가?
/*@Table을 통해 클래스를 movies에 연결한다. 각 필드 위에 @Column을 통해 테이블의 column들에 매핑한다.*/
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String director;

    @Column(nullable = false, length = 50)
    private String genre;

    @Column(nullable = false)
    private Integer runtime;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Integer releaseYear;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Movie() { }

    public Movie(MovieRequest request) {
        this.title = request.title();
        this.director = request.director();
        this.genre = request.genre();
        this.runtime = request.runtime();
        this.rating = request.rating();
        this.releaseYear = request.releaseYear();
    }

    public void update(MovieRequest request) {
        this.title = request.title();
        this.director = request.director();
        this.genre = request.genre();
        this.runtime = request.runtime();
        this.rating = request.rating();
        this.releaseYear = request.releaseYear();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public String getGenre() { return genre; }
    public Integer getRuntime() { return runtime; }
    public Double getRating() { return rating; }
    public Integer getReleaseYear() { return releaseYear; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

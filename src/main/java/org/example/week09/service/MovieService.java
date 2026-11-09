package org.example.week09.service;

import jakarta.transaction.Transactional;
import org.example.week09.dto.MovieRequest;
import org.example.week09.dto.MovieResponse;
import org.example.week09.entity.Movie;
import org.example.week09.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    // Q7. save()를 호출하면 새로운 데이터 등록과 기존 데이터 수정이 어떻게 처리되는가?
    // 이미 존재하는 아이디면 기존 행을 덮어씌우는 UPDATE 쿼리를 실행하고, 새로운 데이터이면 DB애 INSERT 쿼리를 실행한다.
    public MovieResponse create(MovieRequest request) {
        Movie saved = repository.save(new Movie(request));
        return MovieResponse.from(saved);
    }

    public List<MovieResponse> findAll() {
        return repository.findAll().stream().map(MovieResponse::from).toList();
    }

    public MovieResponse findById(Long id) {
        return MovieResponse.from(findEntity(id));
    }

    // Q8. @Transactional과 Dirty Checking은 어떤 관계가 있는가?
    /*@Transactional을 통해 메소드 종료 시점에 JPA가 Dirty Checking을 한다.*/
    @Transactional
    public MovieResponse update(Long id, MovieRequest request) {
        Movie movie = findEntity(id);
        movie.update(request);
        return MovieResponse.from(movie);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found: " + id);
        }
        repository.deleteById(id);
    }
    private Movie findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Movie not found: " + id));
    }
    public List<MovieResponse> searchByGenre(String genre){
        return repository.findByGenre(genre).stream().map(MovieResponse::from).toList();
    }
    public List<MovieResponse> searchByGenreAndMinRating(String genre, Double minRating){
        return  repository.findByGenreAndRatingGreaterThanEqual(genre, minRating).stream().map(MovieResponse::from).toList();
    }
    public List<MovieResponse> searchByGenreOrderByRating(String genre){
        return repository.findByGenreOrderByRatingDesc(genre).stream().map(MovieResponse::from).toList();
    }

}

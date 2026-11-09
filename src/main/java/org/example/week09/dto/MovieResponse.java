package org.example.week09.dto;

import org.example.week09.entity.Movie;

import java.time.LocalDateTime;

public record MovieResponse(
        Long id,
        String title,
        String director,
        String genre,
        Integer runtime,
        Double rating,
        Integer releaseYear,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre(),
                movie.getRuntime(),
                movie.getRating(),
                movie.getReleaseYear(),
                movie.getCreatedAt(),
                movie.getUpdatedAt()
        );
    }
}

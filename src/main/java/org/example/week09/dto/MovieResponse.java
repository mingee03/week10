package org.example.week09.dto;

import org.example.week09.entity.Movie;

public record MovieResponse(
        Long id,
        String title,
        String director,
        String genre,
        Integer runtime,
        Double rating,
        Integer releaseYear
) {
    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre(),
                movie.getRuntime(),
                movie.getRating(),
                movie.getReleaseYear()
        );
    }
}

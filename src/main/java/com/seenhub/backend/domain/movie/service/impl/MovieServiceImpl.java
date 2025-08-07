package com.seenhub.backend.domain.movie.service.impl;

import com.seenhub.backend.domain.movie.dto.MovieSearchDto;
import com.seenhub.backend.domain.movie.entity.Genre;
import com.seenhub.backend.domain.movie.repository.MovieRepository;
import com.seenhub.backend.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieApiServiceImpl movieApiService;

    @Value("${webclient.tmdb-poster-url}")
    private String posterUrl;

    @Override
    public Mono<List<MovieSearchDto>> searchMovie(String title) {

        return movieApiService.findMovieByTitle(title)
                .flatMapMany(movieResponseDto -> Flux.fromIterable(movieResponseDto.getResults()))
                .map(results -> {
                    double rating = results.getVoteAverage();
                    List<Genre> genres = results.getGenreIds().stream()
                            .map(Genre::fromId)
                            .filter(genre -> genre!= null)
                            .collect(Collectors.toList());
                    String thumbnail = results.getPosterPath() != null
                            ? posterUrl + results.getPosterPath()
                            : null;

                    return MovieSearchDto.builder()
                            .title(results.getTitle())
                            .rating(rating)
                            .genres(genres)
                            .thumbnail(thumbnail)
                            .build();

                })
                .collectList();

    }

}

package com.seenhub.backend.domain.movie.service;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.movie.dto.MovieCreateRequestDto;
import com.seenhub.backend.domain.movie.dto.MovieListDto;
import com.seenhub.backend.domain.movie.dto.MovieSearchDto;
import com.seenhub.backend.domain.movie.dto.MovieUpdateRequestDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovieService {

    Mono<List<MovieSearchDto>> searchMovie(String title);
    Mono<Void> createMovie(MovieCreateRequestDto dto);
    Mono<PageResponseDto<MovieListDto>> findMovieList(int page, int size);
    Mono<Void> updateMovie(String id, MovieUpdateRequestDto dto);
    Mono<Void> deleteMovie(String id);

}

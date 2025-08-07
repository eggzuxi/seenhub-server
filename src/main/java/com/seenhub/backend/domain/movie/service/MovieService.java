package com.seenhub.backend.domain.movie.service;

import com.seenhub.backend.domain.movie.dto.MovieSearchDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovieService {

    Mono<List<MovieSearchDto>> searchMovie(String title);

}

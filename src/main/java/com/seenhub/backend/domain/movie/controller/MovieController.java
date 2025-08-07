package com.seenhub.backend.domain.movie.controller;

import com.seenhub.backend.domain.movie.dto.MovieSearchDto;
import com.seenhub.backend.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/search")
    public Mono<ResponseEntity<List<MovieSearchDto>>> searchMovie(@RequestParam String title) {

        return movieService.searchMovie(title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}

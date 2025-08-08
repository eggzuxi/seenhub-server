package com.seenhub.backend.domain.movie.controller;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.movie.dto.MovieCreateRequestDto;
import com.seenhub.backend.domain.movie.dto.MovieListDto;
import com.seenhub.backend.domain.movie.dto.MovieSearchDto;
import com.seenhub.backend.domain.movie.dto.MovieUpdateRequestDto;
import com.seenhub.backend.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/add")
    public Mono<ResponseEntity<Void>> createMovie(@RequestBody MovieCreateRequestDto dto) {

        return movieService.createMovie(dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @GetMapping("/all")
    public Mono<ResponseEntity<PageResponseDto<MovieListDto>>> findMovieList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        return movieService.findMovieList(page, size)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PatchMapping("/edit/{id}")
    public Mono<ResponseEntity<Void>> updateMovie(
            @PathVariable String id,
            @RequestBody MovieUpdateRequestDto dto) {

        return movieService.updateMovie(id, dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteMovie(@PathVariable String id) {

        return movieService.deleteMovie(id)
                .then(Mono.just(ResponseEntity.noContent().build()));

    }

}
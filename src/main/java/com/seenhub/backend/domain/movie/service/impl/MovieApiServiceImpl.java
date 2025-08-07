package com.seenhub.backend.domain.movie.service.impl;

import com.seenhub.backend.domain.movie.dto.MovieResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieApiServiceImpl {

    @Qualifier("movieWebClient")
    private final WebClient movieWebClient;

    @Value("${webclient.tmdb-token}")
    private String accessToken;

    public Mono<MovieResponseDto> findMovieByTitle(String title) {

        String bearerToken = "Bearer " + accessToken;

        return movieWebClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("search/movie")
                    .queryParam("query", title)
                    .build())
                .header("Authorization", bearerToken)
                .retrieve()
                .bodyToMono(MovieResponseDto.class);

    }

}

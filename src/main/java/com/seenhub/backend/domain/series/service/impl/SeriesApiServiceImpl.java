package com.seenhub.backend.domain.series.service.impl;

import com.seenhub.backend.domain.series.dto.SeriesResponseDto;
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
public class SeriesApiServiceImpl {

    @Qualifier("seriesWebClient")
    private final WebClient seriesWebClient;

    @Value("${webclient.tmdb-token}")
    private String accessToken;

    public Mono<SeriesResponseDto> findSeriesByTitle(String title) {

        String bearerToken = "Bearer " + accessToken;

        return seriesWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("search/tv")
                        .queryParam("query", title)
                        .build())
                .header("Authorization", bearerToken)
                .retrieve()
                .bodyToMono(SeriesResponseDto.class);

    }

}

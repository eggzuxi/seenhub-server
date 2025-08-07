package com.seenhub.backend.domain.music.service.impl;

import com.seenhub.backend.domain.music.dto.MusicResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class MusicApiServiceImpl {

    @Qualifier("musicWebClient")
    private final WebClient musicWebClient;

    @Qualifier("thumbnailWebClient")
    private final WebClient thumbnailWebClient;

    public Mono<MusicResponseDto> findMusicByTitle(String title) {

        return musicWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("recording")
                        .queryParam("query", title)
                        .queryParam("fmt", "json")
                        .build())
                .retrieve()
                .bodyToMono(MusicResponseDto.class);

    }

    public Mono<String> findThumbnailByMbid(String mbid) {

        return thumbnailWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(mbid, "front")
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> Mono.empty())
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.empty());

    }

}

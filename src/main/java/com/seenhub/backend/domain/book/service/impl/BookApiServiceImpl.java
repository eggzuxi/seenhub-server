package com.seenhub.backend.domain.book.service.impl;

import com.seenhub.backend.domain.book.dto.BookResponseDto;
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
public class BookApiServiceImpl {

    @Qualifier("bookWebClient")
    private final WebClient bookWebClient;

    @Value("${webclient.kakao-api-key}")
    private String accessKey;

    public Mono<BookResponseDto> findBookByTitle(String title) {

        String kakaoToken = "KakaoAK " + accessKey;

        return bookWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("search/book")
                        .queryParam("target", "title")
                        .queryParam("query", title)
                        .build())
                .header("Authorization", kakaoToken)
                .retrieve()
                .bodyToMono(BookResponseDto.class);

    }

}

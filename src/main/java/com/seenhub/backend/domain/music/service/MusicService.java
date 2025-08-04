package com.seenhub.backend.domain.music.service;

import com.seenhub.backend.domain.music.dto.MusicCreateRequestDto;
import com.seenhub.backend.domain.music.dto.MusicSearchDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MusicService {

    Mono<List<MusicSearchDto>> searchMusic(String title);
    Mono<Void> createMusic(MusicCreateRequestDto dto);

}

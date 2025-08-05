package com.seenhub.backend.domain.music.service;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.music.dto.MusicCreateRequestDto;
import com.seenhub.backend.domain.music.dto.MusicListDto;
import com.seenhub.backend.domain.music.dto.MusicSearchDto;
import com.seenhub.backend.domain.music.dto.MusicUpdateRequestDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MusicService {

    Mono<List<MusicSearchDto>> searchMusic(String title);
    Mono<Void> createMusic(MusicCreateRequestDto dto);
    Mono<PageResponseDto<MusicListDto>> findMusicList(int page, int size);
    Mono<Void> updateMusic(String id, MusicUpdateRequestDto dto);
    Mono<Void> deleteMusic(String id);

}

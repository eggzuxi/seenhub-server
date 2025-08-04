package com.seenhub.backend.domain.music.controller;

import com.seenhub.backend.domain.music.dto.MusicCreateRequestDto;
import com.seenhub.backend.domain.music.dto.MusicSearchDto;
import com.seenhub.backend.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/search")
    public Mono<ResponseEntity<List<MusicSearchDto>>> searchMusic(@RequestParam String title) {

        return musicService.searchMusic(title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Void>> createMusic(@RequestBody MusicCreateRequestDto dto) {

        return musicService.createMusic(dto)
                .then(Mono.just(ResponseEntity.ok().<Void>build()));

    }

}

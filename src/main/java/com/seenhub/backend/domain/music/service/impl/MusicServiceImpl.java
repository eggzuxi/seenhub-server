package com.seenhub.backend.domain.music.service.impl;

import com.seenhub.backend.domain.music.dto.MusicCreateRequestDto;
import com.seenhub.backend.domain.music.dto.MusicSearchDto;
import com.seenhub.backend.domain.music.entity.Music;
import com.seenhub.backend.domain.music.repository.MusicRepository;
import com.seenhub.backend.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final MusicApiServiceImpl musicApiService;

    @Override
    public Mono<List<MusicSearchDto>> searchMusic(String title) {

        return musicApiService.findMusicByTitle(title)
                .flatMapMany(musicResponseDto -> Flux.fromIterable(musicResponseDto.getRecordings()))
                .flatMap(recording -> {
                    String artist = recording.getArtistCredit().get(0).getArtist().getName();
                    String mbid = recording.getReleases().get(0).getReleaseGroup().getReleaseGroupId();

                    return musicApiService.findThumbnailByMbid(mbid)
                            .filter(thumbnail -> thumbnail != null && thumbnail.startsWith("See: "))
                            .map(thumbnail -> {
                                String newThumbnail = thumbnail.replace("See: ", "").trim();
                                return MusicSearchDto.builder()
                                        .title(recording.getTitle())
                                        .artist(artist)
                                        .thumbnail(newThumbnail)
                                        .build();
                            });
                })
                .collectList();

    }

    @Override
    public Mono<Void> createMusic(MusicCreateRequestDto dto) {

        Music music = Music.builder()
                .title(dto.getMusic().getTitle())
                .artist(dto.getMusic().getArtist())
                .genre(dto.getGenre())
                .thumbnail(dto.getMusic().getThumbnail())
                .isMasterPiece(dto.isMasterPiece())
                .build();

        return musicRepository.save(music).then();

    }

}

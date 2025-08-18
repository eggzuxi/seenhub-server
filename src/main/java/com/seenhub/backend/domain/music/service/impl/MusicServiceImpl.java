package com.seenhub.backend.domain.music.service.impl;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.music.dto.MusicCreateRequestDto;
import com.seenhub.backend.domain.music.dto.MusicListDto;
import com.seenhub.backend.domain.music.dto.MusicSearchDto;
import com.seenhub.backend.domain.music.dto.MusicUpdateRequestDto;
import com.seenhub.backend.domain.music.entity.Music;
import com.seenhub.backend.domain.music.repository.MusicRepository;
import com.seenhub.backend.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final ReactiveMongoTemplate mongoTemplate;

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
                .genres(dto.getGenres())
                .thumbnail(dto.getMusic().getThumbnail())
                .isMasterPiece(dto.isMasterPiece())
                .build();

        return musicRepository.save(music).then();

    }

    @Override
    public Mono<PageResponseDto<MusicListDto>> findMusicList(int page, int size) {

        Mono<Long> totalCount = musicRepository.count();

        Query query = new Query(
                Criteria.where("isDeleted").is(false)
        )
                .skip((long)(page - 1) * size)
                .limit(size);

        Mono<List<MusicListDto>> musicList = mongoTemplate.find(query, Music.class)
                .map(music -> MusicListDto.builder()
                        .id(music.getId())
                        .title(music.getTitle())
                        .artist(music.getArtist())
                        .genres(music.getGenres())
                        .thumbnail(music.getThumbnail())
                        .commentId(music.getCommentId())
                        .isMasterPiece(music.isMasterPiece())
                        .build())
                .collectList();

        return Mono.zip(musicList, totalCount)
                .map(tuple -> {
                    List<MusicListDto> content = tuple.getT1();
                    Long totalCnt = tuple.getT2();
                    boolean isLast = (long) (page + 1) * size >= totalCnt;

                    return PageResponseDto.<MusicListDto>builder()
                            .content(content)
                            .pageNumber(page)
                            .pageSize(size)
                            .last(isLast)
                            .build();

                });

    }

    @Override
    public Mono<Void> updateMusic(String id, MusicUpdateRequestDto dto) {

        return musicRepository.findById(id)
                .flatMap(oldMusic -> {

                    Music.MusicBuilder<?, ?> builder = oldMusic.toBuilder();

                    if (dto.getTitle() != null) {
                        builder.title(dto.getTitle());
                    }

                    if (dto.getArtist() != null) {
                        builder.artist(dto.getArtist());
                    }

                    if (dto.getGenres() != null) {
                        builder.genres(dto.getGenres());
                    }

                    if (dto.getThumbnail() != null) {
                        builder.thumbnail(dto.getThumbnail());
                    }

                    if (dto.getIsMasterPiece() != null) {
                        builder.isMasterPiece(dto.getIsMasterPiece());
                    }

                    Music newMusic = builder.build();
                    return musicRepository.save(newMusic);

                })
                .then();

    }

    @Override
    public Mono<Void> deleteMusic(String id) {

        return musicRepository.findById(id)
                .flatMap(oldMusic -> {

                    Music music = oldMusic.toBuilder()
                            .isDeleted(true)
                            .build();

                    return musicRepository.save(music);

                })
                .then();

    }

}

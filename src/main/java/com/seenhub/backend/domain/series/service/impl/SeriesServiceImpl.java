package com.seenhub.backend.domain.series.service.impl;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.series.dto.SeriesCreateRequestDto;
import com.seenhub.backend.domain.series.dto.SeriesListDto;
import com.seenhub.backend.domain.series.dto.SeriesSearchDto;
import com.seenhub.backend.domain.series.dto.SeriesUpdateRequestDto;
import com.seenhub.backend.domain.series.entity.Genre;
import com.seenhub.backend.domain.series.entity.Series;
import com.seenhub.backend.domain.series.repository.SeriesRepository;
import com.seenhub.backend.domain.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesApiServiceImpl seriesApiService;
    private final ReactiveMongoTemplate mongoTemplate;

    @Value("${webclient.tmdb-poster-url}")
    private String posterUrl;

    @Override
    public Mono<List<SeriesSearchDto>> searchSeries(String title) {

        return seriesApiService.findSeriesByTitle(title)
                .flatMapMany(seriesResponseDto -> Flux.fromIterable(seriesResponseDto.getResults()))
                .map(results -> {
                    double rating = Math.round(results.getVoteAverage() * 100.0) / 100.0;
                    List<Genre> genres = results.getGenreIds().stream()
                            .map(Genre::fromId)
                            .filter(genre -> genre != null)
                            .collect(Collectors.toList());
                    String thumbnail = results.getPosterPath() != null
                        ? posterUrl + results.getPosterPath()
                        : null;

                    return SeriesSearchDto.builder()
                            .title(results.getTitle())
                            .rating(rating)
                            .genres(genres)
                            .thumbnail(thumbnail)
                            .build();

                })
                .collectList();

    }

    @Override
    public Mono<Void> createSeries(SeriesCreateRequestDto dto) {

        Series series = Series.builder()
                .title(dto.getSeries().getTitle())
                .genres(dto.getSeries().getGenres())
                .rating(dto.getSeries().getRating())
                .thumbnail(dto.getSeries().getThumbnail())
                .isMasterPiece(dto.isMasterPiece())
                .build();

        return seriesRepository.save(series).then();

    }

    @Override
    public Mono<PageResponseDto<SeriesListDto>> findSeriesList(int page, int size) {

        Mono<Long> totalCount = seriesRepository.count();

        Query query = new Query(
                Criteria.where("isDeleted").is(false)
        )
                .skip((long)(page - 1) * size)
                .limit(size)
                .with(Sort.by(Sort.Direction.DESC, "_id"));

        Mono<List<SeriesListDto>> seriesList = mongoTemplate.find(query, Series.class)
                .map(series -> SeriesListDto.builder()
                        .id(series.getId())
                        .title(series.getTitle())
                        .genres(series.getGenres())
                        .rating(series.getRating())
                        .thumbnail(series.getThumbnail())
                        .commentId(series.getCommentId())
                        .isMasterPiece(series.isMasterPiece())
                        .build())
                .collectList();

        return Mono.zip(seriesList, totalCount)
                .map(tuple -> {
                    List<SeriesListDto> content = tuple.getT1();
                    Long totalCnt = tuple.getT2();
                    boolean isLast = (long) (page + 1) * size >= totalCnt;

                    return PageResponseDto.<SeriesListDto>builder()
                            .content(content)
                            .pageNumber(page)
                            .pageSize(size)
                            .last(isLast)
                            .build();

                });

    }

    @Override
    public Mono<Void> updateSeries(String id, SeriesUpdateRequestDto dto) {

        return seriesRepository.findById(id)
                .flatMap(oldSeries -> {

                    Series.SeriesBuilder<?, ?> builder = oldSeries.toBuilder();

                    if (dto.getTitle() != null) {
                        builder.title(dto.getTitle());
                    }

                    if (dto.getGenres() != null) {
                        builder.genres(dto.getGenres());
                    }

                    if (dto.getRating() != null) {
                        builder.rating(dto.getRating());
                    }

                    if (dto.getThumbnail() != null) {
                        builder.thumbnail(dto.getThumbnail());
                    }

                    if (dto.getIsMasterPiece() != null) {
                        builder.isMasterPiece(dto.getIsMasterPiece());
                    }

                    Series newSeries = builder.build();
                    return seriesRepository.save(newSeries);

                })
                .then();

    }

    @Override
    public Mono<Void> deleteSeries(String id) {

        return seriesRepository.findById(id)
                .flatMap(oldSeries -> {

                    Series series = oldSeries.toBuilder()
                            .isDeleted(true)
                            .build();

                    return seriesRepository.save(series);

                })
                .then();

    }

}

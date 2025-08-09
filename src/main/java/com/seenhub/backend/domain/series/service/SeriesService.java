package com.seenhub.backend.domain.series.service;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.series.dto.SeriesCreateRequestDto;
import com.seenhub.backend.domain.series.dto.SeriesListDto;
import com.seenhub.backend.domain.series.dto.SeriesSearchDto;
import com.seenhub.backend.domain.series.dto.SeriesUpdateRequestDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SeriesService {

    Mono<List<SeriesSearchDto>> searchSeries(String title);
    Mono<Void> createSeries(SeriesCreateRequestDto dto);
    Mono<PageResponseDto<SeriesListDto>> findSeriesList(int page, int size);
    Mono<Void> updateSeries(String id, SeriesUpdateRequestDto dto);
    Mono<Void> deleteSeries(String id);


}

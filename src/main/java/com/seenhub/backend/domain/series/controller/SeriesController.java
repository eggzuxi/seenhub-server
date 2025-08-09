package com.seenhub.backend.domain.series.controller;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.series.dto.SeriesCreateRequestDto;
import com.seenhub.backend.domain.series.dto.SeriesListDto;
import com.seenhub.backend.domain.series.dto.SeriesSearchDto;
import com.seenhub.backend.domain.series.dto.SeriesUpdateRequestDto;
import com.seenhub.backend.domain.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;

    @GetMapping("/search")
    public Mono<ResponseEntity<List<SeriesSearchDto>>> searchSeries(@RequestParam String title) {

        return seriesService.searchSeries(title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Void>> createSeries(@RequestBody SeriesCreateRequestDto dto) {

        return seriesService.createSeries(dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @GetMapping("/all")
    public Mono<ResponseEntity<PageResponseDto<SeriesListDto>>> findSeriesList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        return seriesService.findSeriesList(page, size)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PatchMapping("/edit/{id}")
    public Mono<ResponseEntity<Void>> updateSeries(
            @PathVariable String id,
            @RequestBody SeriesUpdateRequestDto dto) {

        return seriesService.updateSeries(id, dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteSeries(@PathVariable String id) {

        return seriesService.deleteSeries(id)
                .then(Mono.just(ResponseEntity.noContent().build()));

    }

}

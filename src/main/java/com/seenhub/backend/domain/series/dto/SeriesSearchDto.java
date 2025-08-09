package com.seenhub.backend.domain.series.dto;

import com.seenhub.backend.domain.series.entity.Genre;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SeriesSearchDto {

    private String title;
    private List<Genre> genres;
    private double rating;
    private String thumbnail;

}

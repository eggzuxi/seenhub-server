package com.seenhub.backend.domain.series.dto;

import com.seenhub.backend.domain.series.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SeriesUpdateRequestDto {

    private String title;
    private List<Genre> genres;
    private Double rating;
    private String thumbnail;
    private Boolean isMasterPiece;

}

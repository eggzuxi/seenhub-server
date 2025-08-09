package com.seenhub.backend.domain.series.dto;

import com.seenhub.backend.domain.series.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SeriesListDto {

    private String id;
    private String title;
    private List<Genre> genres;
    private double rating;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}

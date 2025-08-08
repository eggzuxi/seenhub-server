package com.seenhub.backend.domain.movie.dto;

import com.seenhub.backend.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieListDto {

    private String id;
    private String title;
    private List<Genre> genres;
    private double rating;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}

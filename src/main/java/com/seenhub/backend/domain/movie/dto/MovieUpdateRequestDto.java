package com.seenhub.backend.domain.movie.dto;

import com.seenhub.backend.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieUpdateRequestDto {

    private String title;
    private List<Genre> genres;
    private Double rating;
    private Boolean isMasterPiece;

}

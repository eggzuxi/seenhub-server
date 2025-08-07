package com.seenhub.backend.domain.movie.dto;

import com.seenhub.backend.domain.movie.entity.Genre;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieSearchDto {

    private String title;
    private List<Genre> genres;
    private double rating;
    private String thumbnail;

}

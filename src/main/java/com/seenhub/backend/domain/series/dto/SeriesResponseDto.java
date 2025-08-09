package com.seenhub.backend.domain.series.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SeriesResponseDto {

    private List<ResultsDto> results;

    @Getter
    @NoArgsConstructor
    public static class ResultsDto {

        @JsonProperty("name")
        private String title;

        @JsonProperty("genre_ids")
        private List<Integer> genreIds;

        @JsonProperty("vote_average")
        private double voteAverage;

        @JsonProperty("poster_path")
        private String posterPath;

    }

}

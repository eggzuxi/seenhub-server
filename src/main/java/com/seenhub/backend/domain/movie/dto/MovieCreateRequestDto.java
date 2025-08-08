package com.seenhub.backend.domain.movie.dto;

import lombok.Data;

@Data
public class MovieCreateRequestDto {

    private MovieSearchDto movie;
    private boolean isMasterPiece;

}

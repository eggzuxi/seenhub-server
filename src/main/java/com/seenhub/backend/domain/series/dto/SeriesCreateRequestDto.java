package com.seenhub.backend.domain.series.dto;

import lombok.Data;

@Data
public class SeriesCreateRequestDto {

    private SeriesSearchDto series;
    private boolean isMasterPiece;

}

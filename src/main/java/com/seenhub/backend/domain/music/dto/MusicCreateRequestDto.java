package com.seenhub.backend.domain.music.dto;

import com.seenhub.backend.domain.music.entity.Genre;
import lombok.Data;

@Data
public class MusicCreateRequestDto {

    private MusicSearchDto music;
    private Genre genre;
    private boolean isMasterPiece;

}

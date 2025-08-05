package com.seenhub.backend.domain.music.dto;

import com.seenhub.backend.domain.music.entity.Genre;
import lombok.Data;

import java.util.List;

@Data
public class MusicCreateRequestDto {

    private MusicSearchDto music;
    private List<Genre> genres;
    private boolean isMasterPiece;

}

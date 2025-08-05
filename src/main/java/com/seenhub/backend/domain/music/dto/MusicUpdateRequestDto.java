package com.seenhub.backend.domain.music.dto;

import com.seenhub.backend.domain.music.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MusicUpdateRequestDto {

    private String title;
    private String artist;
    private List<Genre> genres;
    private Boolean isMasterPiece;

}

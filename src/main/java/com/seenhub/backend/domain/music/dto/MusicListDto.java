package com.seenhub.backend.domain.music.dto;

import com.seenhub.backend.domain.music.entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class MusicListDto {

    private String id;
    private String title;
    private String artist;
    private Genre genre;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}
package com.seenhub.backend.domain.music.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicSearchDto {

    private String title;
    private String artist;
    private String thumbnail;

}

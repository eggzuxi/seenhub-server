package com.seenhub.backend.domain.music.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MusicResponseDto {

    private List<RecordingDto> recordings;

    @Getter
    @NoArgsConstructor
    public static class RecordingDto {

        @JsonProperty("title")
        private String title; // 곡 제목

        @JsonProperty("artist-credit")
        private List<ArtistCreditDto> artistCredit;

        private List<ReleaseDto> releases;
    }

    @Getter
    @NoArgsConstructor
    public static class ArtistCreditDto {
        private ArtistDto artist;
    }

    @Getter
    @NoArgsConstructor
    public static class ArtistDto {
        private String name; // 가수 이름
    }

    @Getter
    @NoArgsConstructor
    public static class ReleaseDto {
        @JsonProperty("release-group")
        private ReleaseGroupDto releaseGroup;
    }

    @Getter
    @NoArgsConstructor
    public static class ReleaseGroupDto {
        @JsonProperty("id")
        private String releaseGroupId; // 썸네일용 MBID
    }

}

package com.seenhub.backend.domain.music.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "musics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music extends Base {

    @Id
    private String id;
    private String title;
    private String artist;
    private Genre genre;
    private String thumbnail;
    private boolean isMasterPiece;

    @Builder(toBuilder = true)
    public Music(String title, String artist, Genre genre, String thumbnail, boolean isMasterPiece) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.thumbnail = thumbnail;
        this.isMasterPiece = isMasterPiece;
    }

}

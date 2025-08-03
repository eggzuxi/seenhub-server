package com.seenhub.backend.domain.movie.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Base {

    @Id
    private String id;
    private String title;
    private String director;
    private Genre genre;
    private String thumbnail;
    private boolean isMasterPiece;

    @Builder(toBuilder = true)
    public Movie(String title, String author, Genre genre, String thumbnail, boolean isMasterPiece) {
        this.title = title;
        this.director = author;
        this.genre = genre;
        this.thumbnail = thumbnail;
        this.isMasterPiece = isMasterPiece;
    }

}

package com.seenhub.backend.domain.movie.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Base {

    @Id
    private String id;
    private String title;
    private String director;
    private Genre genre;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}

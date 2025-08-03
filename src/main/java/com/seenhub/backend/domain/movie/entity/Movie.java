package com.seenhub.backend.domain.movie.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Getter
@Builder(toBuilder = true)
public class Movie extends Base {

    @Id
    private String id;
    private String title;
    private String director;
    private Genre genre;
    private String thumbnail;
    private boolean isMasterPiece;

}

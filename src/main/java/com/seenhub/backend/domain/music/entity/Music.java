package com.seenhub.backend.domain.music.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "musics")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music extends Base {

    @Id
    private String id;
    private String title;
    private String artist;
    private List<Genre> genres;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}

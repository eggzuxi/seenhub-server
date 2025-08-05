package com.seenhub.backend.domain.series.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "series")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Series extends Base {

    @Id
    private String id;
    private String title;
    private String broadcaster;
    private Genre genre;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}

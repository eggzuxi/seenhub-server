package com.seenhub.backend.domain.book.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
@Getter
@Builder(toBuilder = true)
public class Book extends Base {

    @Id
    private String id;
    private String title;
    private String author;
    private Genre genre;
    private String thumbnail;
    private boolean isMasterPiece;

}

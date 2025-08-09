package com.seenhub.backend.domain.book.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookListDto {

    private String id;
    private String title;
    private List<String> authors;
    private String publisher;
    private String thumbnail;
    private String commentId;
    private boolean isMasterPiece;

}

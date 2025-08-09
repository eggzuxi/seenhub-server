package com.seenhub.backend.domain.book.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookUpdateRequestDto {

    private String title;
    private List<String> authors;
    private String publisher;
    private Boolean isMasterPiece;

}

package com.seenhub.backend.domain.book.dto;

import lombok.Data;

@Data
public class BookCreateRequestDto {

    private BookSearchDto book;
    private boolean isMasterPiece;

}

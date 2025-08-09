package com.seenhub.backend.domain.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookResponseDto {

    private List<DocumentsDto> documents;

    @Getter
    @NoArgsConstructor
    public static class DocumentsDto {

        private String title;
        private List<String> authors;
        private String publisher;
        private String thumbnail;

    }

}

package com.seenhub.backend.domain.book.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookSearchDto {

    private String title;
    private List<String> authors;
    private String publisher;
    private String thumbnail;

}

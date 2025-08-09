package com.seenhub.backend.domain.book.service;

import com.seenhub.backend.domain.book.dto.BookCreateRequestDto;
import com.seenhub.backend.domain.book.dto.BookListDto;
import com.seenhub.backend.domain.book.dto.BookSearchDto;
import com.seenhub.backend.domain.book.dto.BookUpdateRequestDto;
import com.seenhub.backend.domain.common.dto.PageResponseDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookService {

    Mono<List<BookSearchDto>> searchBook(String title);
    Mono<Void> createBook(BookCreateRequestDto dto);
    Mono<PageResponseDto<BookListDto>> findBookList(int page, int size);
    Mono<Void> updateBook(String id, BookUpdateRequestDto dto);
    Mono<Void> deleteBook(String id);

}

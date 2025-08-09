package com.seenhub.backend.domain.book.controller;

import com.seenhub.backend.domain.book.dto.BookCreateRequestDto;
import com.seenhub.backend.domain.book.dto.BookListDto;
import com.seenhub.backend.domain.book.dto.BookSearchDto;
import com.seenhub.backend.domain.book.dto.BookUpdateRequestDto;
import com.seenhub.backend.domain.book.service.BookService;
import com.seenhub.backend.domain.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/search")
    public Mono<ResponseEntity<List<BookSearchDto>>> searchBook(@RequestParam String title) {

        return bookService.searchBook(title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Void>> createBook(@RequestBody BookCreateRequestDto dto) {

        return bookService.createBook(dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @GetMapping("/all")
    public Mono<ResponseEntity<PageResponseDto<BookListDto>>> findBookList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        return bookService.findBookList(page, size)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PatchMapping("/edit/{id}")
    public Mono<ResponseEntity<Void>> updateBook(
            @PathVariable String id,
            @RequestBody BookUpdateRequestDto dto) {

        return bookService.updateBook(id, dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable String id) {

        return bookService.deleteBook(id)
                .then(Mono.just(ResponseEntity.noContent().build()));

    }

}

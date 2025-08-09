package com.seenhub.backend.domain.book.service.impl;

import com.seenhub.backend.domain.book.dto.BookCreateRequestDto;
import com.seenhub.backend.domain.book.dto.BookListDto;
import com.seenhub.backend.domain.book.dto.BookSearchDto;
import com.seenhub.backend.domain.book.dto.BookUpdateRequestDto;
import com.seenhub.backend.domain.book.entity.Book;
import com.seenhub.backend.domain.book.repository.BookRepository;
import com.seenhub.backend.domain.book.service.BookService;
import com.seenhub.backend.domain.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookApiServiceImpl bookApiService;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<List<BookSearchDto>> searchBook(String title) {

        return bookApiService.findBookByTitle(title)
                .flatMapMany(bookResponseDto -> Flux.fromIterable(bookResponseDto.getDocuments()))
                .map(documents -> {

                    return BookSearchDto.builder()
                            .title(documents.getTitle())
                            .authors(documents.getAuthors())
                            .publisher(documents.getPublisher())
                            .thumbnail(documents.getThumbnail())
                            .build();

                })
                .collectList();

    }

    @Override
    public Mono<Void> createBook(BookCreateRequestDto dto) {

        Book book = Book.builder()
                .title(dto.getBook().getTitle())
                .authors(dto.getBook().getAuthors())
                .publisher(dto.getBook().getPublisher())
                .thumbnail(dto.getBook().getThumbnail())
                .isMasterPiece(dto.isMasterPiece())
                .build();

        return bookRepository.save(book).then();

    }

    @Override
    public Mono<PageResponseDto<BookListDto>> findBookList(int page, int size) {

        Mono<Long> totalCount = bookRepository.count();

        Query query = new Query(
                Criteria.where("isDeleted").is(false)
        )
                .skip((page - 1) * size)
                .limit(size);

        Mono<List<BookListDto>> bookList = mongoTemplate.find(query, Book.class)
                .map(book -> BookListDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .authors(book.getAuthors())
                        .publisher(book.getPublisher())
                        .thumbnail(book.getThumbnail())
                        .commentId(book.getCommentId())
                        .isMasterPiece(book.isMasterPiece())
                        .build())
                .collectList();

        return Mono.zip(bookList, totalCount)
                .map(tuple -> {
                    List<BookListDto> content = tuple.getT1();
                    Long totalCnt = tuple.getT2();
                    boolean isLast = (long) (page + 1) * size >= totalCnt;

                    return PageResponseDto.<BookListDto>builder()
                            .content(content)
                            .pageNumber(page)
                            .pageSize(size)
                            .last(isLast)
                            .build();
                });

    }

    @Override
    public Mono<Void> updateBook(String id, BookUpdateRequestDto dto) {

        return bookRepository.findById(id)
                .flatMap(oldBook -> {

                    Book.BookBuilder<?, ?> builder = oldBook.toBuilder();

                    if (dto.getTitle() != null) {
                        builder.title(dto.getTitle());
                    }

                    if (dto.getAuthors() != null) {
                        builder.authors(dto.getAuthors());
                    }

                    if (dto.getPublisher() != null) {
                        builder.publisher(dto.getPublisher());
                    }

                    if (dto.getIsMasterPiece() != null) {
                        builder.isMasterPiece(dto.getIsMasterPiece());
                    }

                    Book newBook = builder.build();
                    return bookRepository.save(newBook);

                })
                .then();

    }

    @Override
    public Mono<Void> deleteBook(String id) {

        return bookRepository.findById(id)
                .flatMap(oldBook -> {

                    Book book = oldBook.toBuilder()
                            .isDeleted(true)
                            .build();

                    return bookRepository.save(book);

                })
                .then();

    }

}

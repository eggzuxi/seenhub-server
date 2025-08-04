package com.seenhub.backend.domain.book.repository;

import com.seenhub.backend.domain.book.entity.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {


}

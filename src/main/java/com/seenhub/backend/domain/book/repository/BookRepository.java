package com.seenhub.backend.domain.book.repository;

import com.seenhub.backend.domain.book.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {


}

package com.seenhub.backend.domain.movie.repository;

import com.seenhub.backend.domain.movie.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {



}

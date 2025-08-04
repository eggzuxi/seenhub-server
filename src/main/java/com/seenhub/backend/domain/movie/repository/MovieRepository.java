package com.seenhub.backend.domain.movie.repository;

import com.seenhub.backend.domain.movie.entity.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {



}

package com.seenhub.backend.domain.series.repository;

import com.seenhub.backend.domain.series.entity.Series;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SeriesRepository extends ReactiveMongoRepository<Series, String> {



}

package com.seenhub.backend.domain.series.repository;

import com.seenhub.backend.domain.series.entity.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeriesRepository extends MongoRepository<Series, String> {



}

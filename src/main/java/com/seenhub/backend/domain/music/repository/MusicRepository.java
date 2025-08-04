package com.seenhub.backend.domain.music.repository;

import com.seenhub.backend.domain.music.entity.Music;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MusicRepository extends ReactiveMongoRepository<Music, String> {



}

package com.seenhub.backend.domain.music.repository;

import com.seenhub.backend.domain.music.entity.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicRepository extends MongoRepository<Music, String> {



}

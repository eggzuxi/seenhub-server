package com.seenhub.backend.domain.user.repository;

import com.seenhub.backend.domain.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {



}

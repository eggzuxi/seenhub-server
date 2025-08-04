package com.seenhub.backend.domain.user.repository;

import com.seenhub.backend.domain.user.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {



}

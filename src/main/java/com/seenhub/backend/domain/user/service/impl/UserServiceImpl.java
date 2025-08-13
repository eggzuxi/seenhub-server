package com.seenhub.backend.domain.user.service.impl;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.user.dto.UserLoginDto;
import com.seenhub.backend.domain.user.dto.UserRequestDto;
import com.seenhub.backend.domain.user.dto.UserListDto;
import com.seenhub.backend.domain.user.dto.UserResponseDto;
import com.seenhub.backend.domain.user.entity.User;
import com.seenhub.backend.domain.user.repository.UserRepository;
import com.seenhub.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UserResponseDto> login(UserLoginDto dto) {
        return userRepository.findByUserId(dto.getUserId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    boolean isMatch = passwordEncoder.matches(dto.getPassword(), user.getPassword());
                    if (!isMatch) {
                        return Mono.error(new RuntimeException("Wrong password"));
                    }
                    return Mono.just(UserResponseDto.builder()
                            .userId(user.getUserId())
                            .name(user.getName())
                            .build());
                });
    }

    @Override
    public Mono<Void> createUser(UserRequestDto dto) {

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .userId(dto.getUserId())
                .password(encodedPassword)
                .name(dto.getName())
                .build();

        return userRepository.save(user).then();

    }

    @Override
    public Mono<PageResponseDto<UserListDto>> findUserList(int page, int size) {

        Mono<Long> totalCount = userRepository.count();

        Query query = new Query(
                Criteria.where("isDeleted").is(false)
        )
                .skip((page - 1) * size)
                .limit(size);

        Mono<List<UserListDto>> userList = mongoTemplate.find(query, User.class)
                .map(user -> UserListDto.builder()
                        .id(user.getId())
                        .userId(user.getUserId())
                        .name(user.getName())
                        .build())
                .collectList();

        return Mono.zip(userList, totalCount)
                .map(tuple -> {
                    List<UserListDto> content = tuple.getT1();
                    Long totalCnt = tuple.getT2();
                    boolean isLast = (long) (page + 1) * size >= totalCnt;

                    return PageResponseDto.<UserListDto>builder()
                            .content(content)
                            .pageNumber(page)
                            .pageSize(size)
                            .last(isLast)
                            .build();

                });

    }

    @Override
    public Mono<Void> updateUser(String id, UserRequestDto dto) {

        return userRepository.findById(id)
                .flatMap(oldUser -> {

                    User.UserBuilder<?, ?> builder = oldUser.toBuilder();

                    if (dto.getUserId() != null) {
                        builder.userId(dto.getUserId());
                    }

                    if (dto.getPassword() != null) {
                        String encodedPassword = passwordEncoder.encode(dto.getPassword());
                        builder.password(encodedPassword);
                    }

                    if (dto.getName() != null) {
                        builder.name(dto.getName());
                    }

                    User newUser = builder.build();
                    return userRepository.save(newUser);

                })
                .then();

    }

    @Override
    public Mono<Void> deleteUser(String id) {

        return userRepository.findById(id)
                .flatMap(oldUser -> {

                    User user = oldUser.toBuilder()
                            .isDeleted(true)
                            .build();

                    return userRepository.save(user);

                })
                .then();

    }

}

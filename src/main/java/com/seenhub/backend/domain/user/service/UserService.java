package com.seenhub.backend.domain.user.service;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.user.dto.UserRequestDto;
import com.seenhub.backend.domain.user.dto.UserListDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Void> createUser(UserRequestDto dto);
    Mono<PageResponseDto<UserListDto>> findUserList(int page, int size);
    Mono<Void> updateUser(String id, UserRequestDto dto);
    Mono<Void> deleteUser(String id);

}

package com.seenhub.backend.domain.user.controller;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.user.dto.UserListDto;
import com.seenhub.backend.domain.user.dto.UserRequestDto;
import com.seenhub.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public Mono<ResponseEntity<Void>> createUser(@RequestBody UserRequestDto dto) {

        return userService.createUser(dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @GetMapping("/all")
    public Mono<ResponseEntity<PageResponseDto<UserListDto>>> findUserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        return userService.findUserList(page, size)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PatchMapping("/edit/{id}")
    public Mono<ResponseEntity<Void>> updateUser(
            @PathVariable String id,
            @RequestBody UserRequestDto dto) {

        return userService.updateUser(id, dto)
                .then(Mono.just(ResponseEntity.ok().build()));

    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {

        return userService.deleteUser(id)
                .then(Mono.just(ResponseEntity.noContent().build()));

    }

}

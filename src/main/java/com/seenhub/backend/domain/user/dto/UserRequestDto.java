package com.seenhub.backend.domain.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {

    private String userId;
    private String password;
    private String name;

}

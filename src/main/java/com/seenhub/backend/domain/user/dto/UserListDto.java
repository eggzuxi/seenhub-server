package com.seenhub.backend.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListDto {

    private String id;
    private String userId;
    private String name;

}

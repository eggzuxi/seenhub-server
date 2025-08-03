package com.seenhub.backend.domain.user.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Builder(toBuilder = true)
public class User extends Base {

    @Id
    private String id;
    private String password;
    private String name;

}

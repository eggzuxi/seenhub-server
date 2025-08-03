package com.seenhub.backend.domain.user.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Base {

    @Id
    private String id;
    private String username;
    private String password;
    private String name;

    @Builder(toBuilder = true)
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

}

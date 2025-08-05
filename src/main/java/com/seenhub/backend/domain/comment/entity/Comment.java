package com.seenhub.backend.domain.comment.entity;

import com.seenhub.backend.domain.common.entity.Base;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Base {

    @Id
    private String id;
    private String content;
    private String userId;

    @Builder(toBuilder = true)
    public Comment(String content, String userId) {
        this.content = content;
        this.userId = userId;
    }

}

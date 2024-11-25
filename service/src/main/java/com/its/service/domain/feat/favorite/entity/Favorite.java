package com.its.service.domain.feat.favorite.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "favorites")
public class Favorite {
    @Id
    private String favoriteId;

    private Long userId;       // MySQL의 User ID
    private String questionId; // MongoDB의 Question ID

    @CreatedDate
    private Instant createAt;

    @LastModifiedDate
    private Instant updateAt;
}

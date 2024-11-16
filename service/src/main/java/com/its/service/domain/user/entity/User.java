package com.its.service.domain.user.entity;

import com.its.service.domain.auth.security.util.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", columnDefinition = "ENUM('KAKAO', 'NAVER', 'GOOGLE', 'APPLE')", nullable = false)
    private SocialType registrationType;

    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name; // 이름

    @Column(name="profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'")
    private UserRole userRole; // 기본값 USER


    public enum UserRole {
        USER, ADMIN
    }
}

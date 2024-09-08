package com.its.service.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name="email", unique = true)
    private String email; //loginId

    @Column(name="name")
    private String name; // 이름

    @Column(name="profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'")
    private UserRole userRole; // 기본값 USER

    @Builder
    public User(Long userId, String email, String name, String profileImage, UserRole userRole) {
        this.user_id = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.userRole = userRole;
    }

    public enum UserRole {
        USER, ADMIN
    }
}

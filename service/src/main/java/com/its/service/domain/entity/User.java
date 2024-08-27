package com.its.service.domain.entity;

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
    @Column(name = "id")
    private Long id;

    @Column(name="email", unique = true)
    private String email; //loginId

    @Column(name="name")
    private String name; // 이름

    private String profileImage;

    @Column(nullable = false)
    private Boolean allowance;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'")
    private UserRole userRole; // 기본값 USER

    @Builder
    public User(Long userId, String email, String name, String profileImage, UserRole userRole, Boolean allowance) {
        this.id = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.userRole = userRole;
        this.allowance = allowance;
    }

    public void updateAllowance() {
        this.allowance = true;
    }

    public enum UserRole {
        USER, ADMIN
    }
}

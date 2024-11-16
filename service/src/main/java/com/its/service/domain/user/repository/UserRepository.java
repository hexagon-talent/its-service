package com.its.service.domain.user.repository;

import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRegistrationType(String email, SocialType registrationType);
}

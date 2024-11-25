package com.its.service.domain.feat.favorite.repository;

import com.its.service.domain.feat.favorite.entity.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    Optional<Favorite> findByUserIdAndQuestionId(Long userId, String questionId);
    List<Favorite> findAllByUserId(Long userId);
    Optional<Favorite> findTopByUserIdOrderByCreateAtDesc(Long userId);
    void deleteByUserIdAndQuestionId(Long userId, String questionId);
    void deleteAllByUserId(Long userId);
    boolean existsByUserIdAndQuestionId(Long userId, String questionId);
}

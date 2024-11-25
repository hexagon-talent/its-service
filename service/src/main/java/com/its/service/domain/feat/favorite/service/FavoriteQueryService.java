package com.its.service.domain.feat.favorite.service;

import com.its.service.common.error.code.FavoriteErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.feat.favorite.dto.response.FavoriteResponse;
import com.its.service.domain.feat.favorite.dto.response.FavoriteResponses;
import com.its.service.domain.feat.favorite.entity.Favorite;
import com.its.service.domain.feat.favorite.mapper.FavoriteMapper;
import com.its.service.domain.feat.favorite.repository.FavoriteRepository;
import com.its.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteQueryService {
    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;
    private final UserQueryService userQueryService;

    public FavoriteResponse getLatestFavorite(Long userId) {
        userQueryService.findUserById(userId);
        Favorite favorite = favoriteRepository.findTopByUserIdOrderByCreateAtDesc(userId).orElseThrow(() -> new CustomException(FavoriteErrorCode.FAVORITE_NOT_FOUND));
        return favoriteMapper.toResponse(favorite);
    }

    public FavoriteResponse getFavorite(Long userId, String questionId) {
        userQueryService.findUserById(userId);
        Favorite favorite = findFavoriteByUserIAndQuestionId(userId, questionId);
        return favoriteMapper.toResponse(favorite);
    }

    public FavoriteResponses getFavorites(Long userId) {
        userQueryService.findUserById(userId);
        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);
        return favoriteMapper.toResponses(favorites);
    }

    public Favorite findFavoriteByUserIAndQuestionId(Long userId, String questionId) {
        return favoriteRepository.findByUserIdAndQuestionId(userId, questionId).orElseThrow(() -> new CustomException(FavoriteErrorCode.FAVORITE_NOT_FOUND));
    }

    public boolean existByUserIAndQuestionId(Long userId, String questionId) {
        return favoriteRepository.existsByUserIdAndQuestionId(userId, questionId);
    }
}

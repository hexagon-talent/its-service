package com.its.service.domain.feat.favorite.service;

import com.its.service.common.error.code.FavoriteErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.feat.favorite.entity.Favorite;
import com.its.service.domain.feat.favorite.mapper.FavoriteMapper;
import com.its.service.domain.feat.favorite.repository.FavoriteRepository;
import com.its.service.domain.question.service.question.QuestionQueryService;
import com.its.service.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCommandService {
    private final FavoriteRepository favoriteRepository;
    private final UserQueryService userQueryService;
    private final QuestionQueryService questionQueryService;
    private final FavoriteQueryService favoriteQueryService;
    private final FavoriteMapper favoriteMapper;

    public Void addFavorite(Long userId, String questionId) {
        userQueryService.findUserById(userId);
        questionQueryService.getQuestionById(questionId);
        boolean isValid = favoriteQueryService.existByUserIAndQuestionId(userId, questionId);
        if (isValid) {
            throw new CustomException(FavoriteErrorCode.FAVORITE_ALREADY_EXISTS);
        }
        Favorite entity = favoriteMapper.toEntity(userId, questionId);
        favoriteRepository.save(entity);
        return null;
    }

    public Void removeFavorite(Long userId, String questionId) {
        userQueryService.findUserById(userId);
        questionQueryService.getQuestionById(questionId);
        boolean isValid = favoriteQueryService.existByUserIAndQuestionId(userId, questionId);
        if (isValid) {
            throw new CustomException(FavoriteErrorCode.FAVORITE_ALREADY_EXISTS);
        }
        favoriteRepository.deleteByUserIdAndQuestionId(userId, questionId);
        return null;
    }

}

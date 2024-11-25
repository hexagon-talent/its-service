package com.its.service.domain.feat.favorite.controller;

import com.its.service.common.response.SuccessResponse;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.domain.feat.favorite.dto.response.FavoriteResponse;
import com.its.service.domain.feat.favorite.dto.response.FavoriteResponses;
import com.its.service.domain.feat.favorite.service.FavoriteCommandService;
import com.its.service.domain.feat.favorite.service.FavoriteQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "즐겨찾기", description = "Favorite API")
public class FavoriteController {
    private final FavoriteCommandService favoriteCommandService;
    private final FavoriteQueryService favoriteQueryService;

    @Operation(summary = "✅ [사용자] 즐겨찾기 등록", description = "특정 문제를 즐겨찾기에 추가하는 API")
    @PostMapping("/{questionId}")
    public ResponseEntity<SuccessResponse<Void>> addFavorite(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @PathVariable String questionId) {

        Long userId = customOAuth2User.getUserId();
        var result = favoriteCommandService.addFavorite(userId, questionId);
        return ResponseFactory.success(result);
    }

    @Operation(summary = "✅ [사용자] 즐겨찾기 해제", description = "특정 문제를 즐겨찾기에서 제거하는 API")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<SuccessResponse<Void>> removeFavorite(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @PathVariable String questionId) {

        Long userId = customOAuth2User.getUserId();
        var result = favoriteCommandService.removeFavorite(userId, questionId);
        return ResponseFactory.success(result);
    }

    @Operation(summary = "✅ [사용자] 즐겨찾기 목록 조회", description = "사용자의 즐겨찾기 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<SuccessResponse<FavoriteResponses>> getFavorites(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        Long userId = customOAuth2User.getUserId();
        var result = favoriteQueryService.getFavorites(userId);
        return ResponseFactory.success(result);
    }

    @Operation(summary = "✅ [사용자] 가장 최근에 추가한 즐겨찾기 조회", description = "사용자가 가장 최근에 추가한 즐겨찾기를 조회하는 API")
    @GetMapping("/latest")
    public ResponseEntity<SuccessResponse<FavoriteResponse>> getLatestFavorite(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        Long userId = customOAuth2User.getUserId();
        var result = favoriteQueryService.getLatestFavorite(userId);
        return ResponseFactory.success(result);
    }
}

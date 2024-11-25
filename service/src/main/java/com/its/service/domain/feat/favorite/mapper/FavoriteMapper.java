package com.its.service.domain.feat.favorite.mapper;

import com.its.service.domain.feat.favorite.dto.response.FavoriteResponse;
import com.its.service.domain.feat.favorite.dto.response.FavoriteResponses;
import com.its.service.domain.feat.favorite.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(target = "favoriteId", ignore = true) // ID는 MongoDB에서 자동 생성되므로 무시
    @Mapping(target = "createAt", ignore = true) // 생성 시 자동 설정
    @Mapping(target = "updateAt", ignore = true) // 수정 시 자동 설정
    Favorite toEntity(Long userId, String questionId);

    default List<String> toQuestionIdList(List<Favorite> favorites){
        return favorites.stream().map(Favorite::getQuestionId).toList();
    }

    // 단일 Favorite을 FavoriteResponse로 변환
    default FavoriteResponse toResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .questionId(favorite.getQuestionId())
                .build();
    }

    // Favorite 리스트를 FavoriteResponses로 변환
    default FavoriteResponses toResponses(List<Favorite> favorites) {
        List<String> favoriteQuestionIds = favorites.stream()
                .map(Favorite::getQuestionId)
                .toList();

        return FavoriteResponses.builder()
                .favoriteQuestionIds(favoriteQuestionIds)
                .build();
    }
}

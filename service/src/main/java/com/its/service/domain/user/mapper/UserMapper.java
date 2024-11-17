package com.its.service.domain.user.mapper;

import com.its.service.domain.user.dto.UserInfoResponse;
import com.its.service.domain.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoResponse toInfoResponse(User user);
}

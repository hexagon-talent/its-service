package com.its.service.domain.auth.controller;

import com.its.service.common.response.SuccessResponse;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.domain.auth.dto.response.RefreshResponse;
import com.its.service.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증", description = "Auth API")
public class AuthController {
    private final AuthService authService;

//    @Operation(summary = "accessToken 재발급")
//    @PostMapping("/token/reissue")
//    public ResponseEntity<SuccessResponse<RefreshResponse>> reissueToken(HttpServletRequest request) {
//        String refreshToken = request.refreshToken();
//        var result = authService.reissueToken(refreshToken);
//        return ResponseFactory.success(response);
//    }
}

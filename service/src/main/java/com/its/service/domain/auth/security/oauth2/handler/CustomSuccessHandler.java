package com.its.service.domain.auth.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.domain.auth.dto.response.LoginResponse;
import com.its.service.domain.auth.mapper.AuthMapper;
import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.auth.service.TokenService;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final AuthMapper authMapper;
    private final ObjectMapper objectMapper;

    @Value("${env.base-url}") private String backendBaseURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = customOauth2User.getEmail();
        String role = customOauth2User.getRole().toString();
        SocialType registrationType = customOauth2User.getRegistrationType();

        // jwt 생성
        String accessToken = tokenService.createAccessToken(registrationType, email, role);
        String refreshToken = tokenService.createRefreshToken(registrationType, email);

        LoginResponse loginResponse = authMapper.toLoginResponse(accessToken, refreshToken);
        var result = ResponseFactory.success(loginResponse);

        // 응답을 JSON 형태로 설정
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));


    }
}

package com.its.service.domain.auth.security.oauth2.handler;

import com.its.service.domain.auth.service.TokenService;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.domain.auth.service.CookieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final CookieService cookieService;

//    private String authRedirectUrl = "http://localhost:8080";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = customOauth2User.getEmail();
        String role = customOauth2User.getRole().toString();


        // jwt 생성
        String accessToken = tokenService.createAccessToken(email, role);
        String refreshToken = tokenService.createRefreshToken(email);

        // 쿠키에 jwt 추가
        response.addCookie(cookieService.createAccessTokenCookie(accessToken));
        response.addCookie(cookieService.createRefreshTokenCookie(refreshToken));
        response.sendRedirect("http://localhost:8080/swagger-ui/index.html#");
    }
}

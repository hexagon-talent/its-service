package com.its.service.security.filter;

import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.entity.User;
import com.its.service.security.oauth2.dto.OAuth2UserDTO;
import com.its.service.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.security.service.TokenService;
import com.its.service.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final String jwtHeader;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtUtil.resolveAccessToken(request);
            if (!jwtUtil.isExpired(accessToken)) {

                User user = tokenService.getUserByAccessToken(accessToken);
                log.info("[AUTH_INFO] 사용자 인가: ID:{} 이름:{}", user.getId(), user.getName());
                OAuth2UserDTO oauth2UserDTO = OAuth2UserDTO.from(user);
                CustomOAuth2User customOAuth2User = new CustomOAuth2User(oauth2UserDTO);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        customOAuth2User,
                        null,
                        customOAuth2User.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException ex) {
            request.setAttribute("error", ex.getErrorCode());
        } catch (Exception e) {
            log.error("[AUTH_ERROR] 사용자 인가 과정에서 에러 발생: {}", e.getMessage());
            request.setAttribute("error", null);
        }

    // 로그인하지 않은 사용자 SecurityContext 에 없으므로 403
    filterChain.doFilter(request, response);
    }
}

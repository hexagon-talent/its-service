package com.its.service.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.common.response.factory.ResponseFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("[AUTH_WARNING] 권한이 없는 경로 {} 에 대한 요청 {}",request.getRequestURI(), accessDeniedException.getMessage());
        ResponseEntity<Object> errorResponse = ResponseFactory.failure(AuthErrorCode.ACCESS_DENIED);
        response.setContentType("application/json");
        // charset=UTF-8 을 붙이지 않으면 message 가 ??? 로 깨져서 표시됨
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorResponse.getStatusCode().value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse.getBody()));
        response.getWriter().flush();
    }
}
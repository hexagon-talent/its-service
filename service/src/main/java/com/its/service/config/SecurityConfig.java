package com.its.service.config;

import com.its.service.security.filter.JWTFilter;
import com.its.service.security.handler.CustomAccessDeniedHandler;
import com.its.service.security.handler.CustomAuthenticationEntryPoint;
import com.its.service.security.oauth2.handler.CustomSuccessHandler;
import com.its.service.security.oauth2.service.CustomOAuth2UserService;
import com.its.service.security.service.TokenService;
import com.its.service.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/public/**"),
                new AntPathRequestMatcher("/favicon.ico"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/v3/api-docs/**")
        );
    }
    // cors 구성
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:3000",
                        "http://localhost:8080"
                )
        );
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.addAllowedHeader("*");
        config.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain oauth2filterChain(HttpSecurity http) throws Exception {
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);      //csrf disable
        http.formLogin(AbstractHttpConfigurer::disable); //From 로그인 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable); //HTTP Basic 인증 방식 disable
        //oauth2
        http.oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler));

        //경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated());

        //세션 설정 : STATELESS
        http.sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http, JWTUtil jwtUtil, TokenService tokenService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
        http
                .securityMatchers(auth -> auth
                        .requestMatchers(
                                "/api/v1/**"
                        )
                )
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                /**
                 * JwtAuthFilter 를 @Component 로 등록하면 WebSecurityCustomizer 에서 ignoring 을 해준 경로에도 jwt 필터가 실행된다.
                 * 관련 링크: https://stackoverflow.com/questions/39152803/spring-websecurity-ignoring-doesnt-ignore-custom-filter/40969780#40969780
                 * shouldNotFilter 로 jwt 필터에서 해당 경로를 타지 않도록 해주거나
                 * 아래처럼 컴포넌트로 등록하지 않고, 수동으로 등록하는 방법을 사용할 수 있다.
                 */
                .addFilterBefore(new JWTFilter(jwtUtil,tokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                        // 헬스 체크 경로는 jwt 인증 비활성화
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        // 헬스 체크 경로는 jwt 인증 비활성화
                        .requestMatchers(
                                "/api/v1",
                                "/api/v1/health",
                                "/api/v1/auth/terms",
                                "/api/v1/dev/**"
                        ).permitAll()
                        // 이외 요청 모두 jwt 필터를 타도록 설정
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                /**
                                 *  인증되지 않은 요청일 경우
                                 *
                                 *  SecurityContext 에 등록되지 않았을 때 호출된다.
                                 */
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                /**
                                 * 인증은 되었으나, 해당 요청에 대한 권한이 없는 사용자인 경우
                                 *
                                 * .hasRole 로 권한을 검사할 때 권한이 부족하여 요청이 거부되었을 때 호출된다.
                                 */
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
package com.its.service.config;



import com.its.service.domain.auth.security.util.SocialType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${env.base-url}")
    private String backendBaseURL;


    private final String SOCIAL_TAG_NAME = "소셜 로그인";

    @Bean
    public OpenAPI OpenApiConfig(OpenApiCustomizer openApiCustomizer) {
        // Servers 에 표시되는 정보 설정
        Server server = new Server();
        server.setUrl(backendBaseURL);
        server.setDescription("ITS Server API");

        OpenAPI openApi = new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                // 기본적으로 모든 엔드포인트에 대한 JWT 인증이 필요한 것으로 설정
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"))
                .info(apiInfo())
                // 서버 정보 추가
                .servers(List.of(server))
                .tags(List.of(new Tag().name(SOCIAL_TAG_NAME).description("Oauth2 Endpoint")))
                .path("/oauth2/authorization/naver", oauth2PathItem(SocialType.NAVER))
                .path("/oauth2/authorization/kakao", oauth2PathItem(SocialType.KAKAO))
                .path("/oauth2/authorization/google", oauth2PathItem(SocialType.GOOGLE))
                .path("/oauth2/authorization/apple", oauth2PathItem(SocialType.APPLE));

        openApiCustomizer.customise(openApi);

        return openApi;
    }
    private PathItem oauth2PathItem(SocialType socialLoginType) {
        String socialId = socialLoginType.getRegistrationId();
        String socialTitle = socialLoginType.getTitle();
        return new PathItem().get(new Operation()
                .tags(List.of(SOCIAL_TAG_NAME))
                .summary(socialTitle)
                .security(List.of()) // 인증 비활성화
                .description(String.format("[%s](%s/oauth2/authorization/%s)", socialTitle, backendBaseURL, socialId))
        );

    }
    private Info apiInfo() {
        return new Info()
                .title("ITS Server API") // API의 제목
                .description("MVP API 명세서") // API에 대한 설명
                .version("0.1.1"); // API의 버전
    }
}


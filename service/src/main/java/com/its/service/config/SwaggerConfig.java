package com.its.service.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        Components components = new Components()
                .addSecuritySchemes(jwt,
                        new SecurityScheme()
                                .name(jwt)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );


        return new OpenAPI()
                .info(apiInfo())
                .components(components);
    }
    private Info apiInfo() {
        return new Info()
                .title("ITS API") // API의 제목
                .description("MVP") // API에 대한 설명
                .version("0.0.1"); // API의 버전
    }
}

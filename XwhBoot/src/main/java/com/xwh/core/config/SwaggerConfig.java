package com.xwh.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String projectName;

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Authorization").bearerFormat("JWT");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer ");
        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        securityRequirements.add(securityRequirement);

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Bearer ",securityScheme))
                //全局配置请求头token
                .security(securityRequirements)
                .info(new Info()
                        .title(projectName+ "-API"));
    }

    @Bean
    public GroupedOpenApi userApi(){
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer ");
        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        securityRequirements.add(securityRequirement);
        String[] paths = { "/**" };
        String[] packagedToMatch = { "com.xwh" };
        return GroupedOpenApi.builder().group(projectName)
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> {
                    return operation.security(securityRequirements);
                })
                .packagesToScan(packagedToMatch).build();
    }
}

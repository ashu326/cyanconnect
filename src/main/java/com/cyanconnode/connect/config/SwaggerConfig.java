package com.cyanconnode.connect.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("CyanConnect APIs")
                        .description("This API documentation provides details of all available endpoints \n" +
                                "for the CyanConnect backend application.\n" +
                                "\n" +
                                "Currently implemented modules:\n" +
                                "- User Management (Add User, Authentication/Login)\n" +
                                "- Project Management (Add Project)\n" +
                                "\n" +
                                "Authentication is handled using JWT-based security.\n" +
                                "All secured endpoints require a valid Bearer token.\n" +
                                "\n" +
                                "Environment: Development")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
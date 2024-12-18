package com.app.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Blog App APIs",
                description = "Blog App APIs that provides endpoints for managing blog posts, comments, users, authentication, and other related resources.",
                summary = "Summary of Blog App APIs",
                termsOfService = "T&C",
                contact = @Contact(
                        name = "Abhishek",
                        email = "tabhishek451@gmail.com"
                ),
                license = @License(
                        name = "AB3C7D9E2F"
                ),
                version = "v1"
        ),
        servers = {
                @Server(
                        description = "Dev",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Test",
                        url = "http://localhost:8081"
                )
        }
)
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

}

package com.dev.auth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class JwtTokenCustomizer {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            context.getClaims().claim("client_id",
                    context.getRegisteredClient().getClientId());
            context.getClaims().claim("roles", "SERVICE");
        };
    }
}

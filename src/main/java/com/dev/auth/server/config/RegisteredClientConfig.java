package com.dev.auth.server.config;

import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
public class RegisteredClientConfig {

	@Bean
	public RegisteredClientRepository registeredClientRepository(
	        JdbcTemplate jdbcTemplate,
	        PasswordEncoder passwordEncoder) {

	    JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcTemplate);

	    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
	            .clientId("client-app")
	            .clientSecret(passwordEncoder.encode("client-secret"))
	            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
	            .scope("read")
	            .scope("write")
	            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
	            .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30)).build())
	            .build();

	    repository.save(client);

	    return repository;
	}
	
	/**    @Bean
    public RegisteredClientRepository registeredClientRepository(
            PasswordEncoder passwordEncoder) {

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-app")
                .clientSecret(passwordEncoder.encode("client-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("read")
                .scope("write")
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }*/
}

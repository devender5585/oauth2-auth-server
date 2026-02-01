package com.dev.auth.server.config;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

	/*
	 * Added startup-time OAuth2 client registration using
	 * JdbcRegisteredClientRepository. This is intended for learning and demo
	 * purposes only.
	 * 
	 * - Registers a demo client (client-app) with client_credentials grant 
	 * - Prevents duplicate registration by checking existing client_id 
	 * - Uses short-lived access tokens and secret expiry 
	 * - In production, client provisioning should be handled via DB/admin tooling
	 */
		
	@Bean
	public RegisteredClientRepository registeredClientRepository(
	        JdbcTemplate jdbcTemplate,
	        PasswordEncoder passwordEncoder) {

	    JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcTemplate);

	    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
	            .clientId("client-app")
	            .clientName("Client Application")
	            .clientSecret(passwordEncoder.encode("client-secret"))
	            .clientSecretExpiresAt(Instant.now().plus(365, ChronoUnit.DAYS)) // 🔥 VERY IMPORTANT
	            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
	            .scope("read")
	            .scope("write")
	            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
	            .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30)).build())
	            .build();

	    if(repository.findByClientId("client-app") ==null) {
	    	repository.save(client);
	    }

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

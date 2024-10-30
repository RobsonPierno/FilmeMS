package com.streaming.filme.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
        		auth -> auth.anyRequest().authenticated() // exige autenticação para todos os endpoints
        )
        .oauth2ResourceServer(
        		oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())) // define que o Resource Server usa tokens JWT
        );
        return http.build();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        // Configura o JwtDecoder com a chave pública do servidor de autenticação (exemplo com URI do endpoint JWK)
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }
}
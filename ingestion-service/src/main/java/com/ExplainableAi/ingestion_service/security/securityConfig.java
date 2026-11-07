package com.ExplainableAi.ingestion_service.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration 
@EnableWebSecurity 
@EnableMethodSecurity
public class securityConfig {
    

    @Bean  
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) {
        return http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth ->
                     auth.requestMatchers("/api/*")
                     .authenticated()
                     
                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract the "realm_access" object from the Keycloak JWT
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            
            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                return Collections.emptyList();
            }
            
            // Extract the "roles" array
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            
            // Convert to Spring Security GrantedAuthority objects with a "ROLE_" prefix
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toList());
        });
        
        return converter;
    }

}

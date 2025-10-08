package com.firstproject.blog_project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
	            .requestMatchers(HttpMethod.GET, "/blogs/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/blogs/**").authenticated()
	            .requestMatchers(HttpMethod.PUT, "/blogs/**").authenticated()
	            .requestMatchers(HttpMethod.DELETE, "/blogs/**").authenticated()
	            .anyRequest().authenticated()
	        )
	        .oauth2Login(Customizer.withDefaults()) // Google OAuth login
	        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // JWT validation
	    return http.build();
	}

}

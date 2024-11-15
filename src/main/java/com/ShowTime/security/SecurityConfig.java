package com.ShowTime.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Autoriser toutes les requêtes sans authentification
                .authorizeRequests(requests -> requests
                                .anyRequest().permitAll() 
                )

                // Désactivation du login (formLogin)
                .formLogin(login -> login.disable()) 

                // Désactivation de l'authentification HTTP de base
                .httpBasic(basic -> basic.disable())

                // Désactivation de la gestion des sessions pour éviter l'expiration et les redirections
                .sessionManagement(management -> management.disable())

                // Désactivation CSRF (utile si vous utilisez H2 console)
                .csrf(csrf -> csrf.disable());
                http.csrf(csrf -> csrf.disable());
                http.headers(headers -> headers.frameOptions().disable());

        return http.build();
    }
}

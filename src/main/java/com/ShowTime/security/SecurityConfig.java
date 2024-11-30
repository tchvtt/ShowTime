package com.ShowTime.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            
            .authorizeRequests(requests -> requests
                .requestMatchers("/profile").authenticated() 
                .anyRequest().permitAll()
            )
 
            .formLogin(login -> login
                .loginPage("/login") 
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/profile", true) 
                .failureUrl("/login?error=true") 
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout") 
                .permitAll()
            )

            .csrf(csrf -> csrf.disable());
            http.csrf(csrf -> csrf.disable());
            http.headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    @SuppressWarnings("removal")
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}

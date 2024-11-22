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

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }


    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
                /*
                .requestMatchers("/login", "/register", "/h2-console").permitAll() //Autorise accès public aux pages de login, inscription et la console de bdd
                .requestMatchers("/profile").authenticated()
                .anyRequest().authenticated() //Authentification nécessaire pour toutes les autres pages
                */
                .requestMatchers("/profile").authenticated() // Nécessite authentification pour /profile
                .anyRequest().permitAll()
            )
 
            .formLogin(login -> login
                .loginPage("/login") //Page de login 
                .defaultSuccessUrl("/", true) //Redirection après connexion réussie
                .failureUrl("/login?error=true") //Redirection si connexion échouée
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL de déconnexion
                .logoutSuccessUrl("/login?logout") //Redirection après déconnexion
                .permitAll()
            )

            // Désactivation CSRF (utile si vous utilisez H2 console)
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

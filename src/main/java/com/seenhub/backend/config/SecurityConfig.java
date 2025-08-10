package com.seenhub.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.seenhub.backend.constants.ApiConstants.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(MUSIC_LIST_URL + "/**").permitAll()
                        .pathMatchers(MOVIE_LIST_URL + "/**").permitAll()
                        .pathMatchers(SERIES_LIST_URL + "/**").permitAll()
                        .pathMatchers(BOOK_LIST_URL + "/**").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }

}

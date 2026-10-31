package com.proyectointegrador.msusuarios.config;

import com.proyectointegrador.msusuarios.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/usuarios/login",
                                "/usuarios/cliente",
                                "/error"
                        ).permitAll()
                        .requestMatchers("/usuarios/empleado")
                        .hasRole("PROPIETARIO")
                        .requestMatchers("/usuarios/propietario")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/administrador")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/usuarios/*/celular")
                        .hasRole("EMPLEADO")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
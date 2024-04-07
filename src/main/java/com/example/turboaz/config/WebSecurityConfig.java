package com.example.turboaz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(permitSwagger).permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/auth/delete/**").hasAnyRole(ADMIN)
                                .requestMatchers(HttpMethod.GET,"/users/details").authenticated()
                                .requestMatchers(HttpMethod.PUT,"/users/update").authenticated()
                                .requestMatchers(HttpMethod.PATCH,"/users/change-password").authenticated()
                                .requestMatchers(HttpMethod.GET,"/users/status").authenticated()
                                .requestMatchers(HttpMethod.PATCH,"/users/status/update").authenticated()
                                .requestMatchers(HttpMethod.GET,"/users/list").authenticated()
                                .requestMatchers(HttpMethod.POST,"/cars").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.DELETE,"/cars/**").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.PUT,"/cars/**").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.GET,"/cars/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/cars").permitAll()
                                .requestMatchers(HttpMethod.POST,"/images").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.DELETE,"/images/**").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.PUT,"/images/**").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.GET,"/images/list/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/reviews").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.DELETE,"/reviews/**").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.PUT,"/reviews/**").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.GET,"/reviews/list/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/transactions").hasAnyRole(USER)
                                .requestMatchers(HttpMethod.PUT,"/transactions/update-status").hasAnyRole(ADMIN)
                                .requestMatchers(HttpMethod.POST,"/transactions/cancel").hasAnyRole(ADMIN)
                                .requestMatchers(HttpMethod.GET,"/transactions/history/**").hasAnyRole(USER)
                                .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public static String[] USER = {
            "USER",
            "ADMIN"
    };

    public static String[] ADMIN = {
            "ADMIN"
    };

    public static String[] permitSwagger = {
            "v3/api-docs/**",
            "v3/api-docs.yaml",
            "swagger-ui/**",
            "/swagger-ui.html"
    };

}
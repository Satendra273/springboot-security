package com.satya.SecApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satya.SecApp.JwtAuthenticationFilter;
import com.satya.SecApp.service.UserServiceImp;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigJwt {

    private final UserServiceImp userServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfigJwt(UserServiceImp userServiceImp , JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userServiceImp = userServiceImp;
        this.jwtAuthenticationFilter= jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




@Bean
SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
    return httpSecurity
            .csrf(csrf->csrf.disable())
            //manage sesseion policy
            .sessionManagement(session->
                    session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
           //exception handling
            .exceptionHandling(ex->ex.authenticationEntryPoint(
                    authenticationEntryPoint()
            ))
            .authorizeHttpRequests(auth->auth
                    .requestMatchers("/auth/register",
                            "/auth/login",
                            "/auth/validate"
                    ).permitAll()
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                    .anyRequest().authenticated()

            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }




    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userServiceImp);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {

        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(
                    MediaType.APPLICATION_JSON_VALUE
            );

            Map<String, Object> errorResponse = Map.of(
                    "status", 401,
                    "error", "UNAUTHORIZED",
                    "message", "Authentication is required. Please provide a valid JWT token."
            );

            new ObjectMapper().writeValue(
                    response.getWriter(),
                    errorResponse
            );
        };
    }
}
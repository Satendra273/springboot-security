//package com.satya.SecApp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.net.http.HttpClient;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//
//
////    @Bean
////    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
////        return httpSecurity
////                .csrf(csrf->csrf.disable())
////                .authorizeHttpRequests(auth->auth
////                        .requestMatchers("/auth/register",
////                                "/auth/login",
////                                "/auth/validate"
////                        ).permitAll()
////                        .anyRequest().authenticated())
////                .build();
////    }
//
//                // Browser form login
////                .formLogin(form -> form
////
////                        .permitAll()
////                )
//
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .permitAll()
////                )
//
//
//
//
//
//@Bean
//SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
//    return httpSecurity
//            .csrf(csrf->csrf.disable())
//            .authorizeHttpRequests(auth->auth
//                    .requestMatchers("/auth/register",
//                            "/auth/login",
//                            "/auth/validate"
//                    ).permitAll()
//                    .anyRequest().authenticated()
//
//            )   .httpBasic(Customizer.withDefaults())
//            .build();
//    }
//
//}
package com.satya.SecApp.config;


import com.satya.SecApp.entity.Role;
import com.satya.SecApp.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepo roleRepo;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {

            if (!roleRepo.existsByName("ROLE_ADMIN")) {
                roleRepo.save(
                        Role.builder()
                                .name("ROLE_ADMIN")
                                .build()
                );
            }

            if (!roleRepo.existsByName("ROLE_USER")) {
                roleRepo.save(
                        Role.builder()
                                .name("ROLE_USER")
                                .build()
                );
            }
        };
    }
}
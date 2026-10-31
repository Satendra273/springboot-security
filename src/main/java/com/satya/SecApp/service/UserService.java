package com.satya.SecApp.service;

import com.satya.SecApp.dto.RegisterRequest;
import com.satya.SecApp.entity.Role;
import com.satya.SecApp.entity.User;
import com.satya.SecApp.repo.RoleRepo;
import com.satya.SecApp.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Role userRole=  roleRepo.save(roleRepo.findByName("ROLE_ADMIN").orElseThrow(()->new RuntimeException("role is not found")));
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .roles( Set.of( userRole ))
                .build();
        return userRepository.save(user);
    }
}

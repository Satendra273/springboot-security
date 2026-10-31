package com.satya.SecApp.service;

import com.satya.SecApp.entity.Role;
import com.satya.SecApp.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public Role createRole(String roleName) {

        if (roleRepo.existsByName(roleName)) {
            throw new RuntimeException("Role already exists");
        }

        Role role = Role.builder()
                .name(roleName)
                .build();

        return roleRepo.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
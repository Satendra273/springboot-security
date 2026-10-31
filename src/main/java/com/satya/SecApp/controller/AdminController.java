package com.satya.SecApp.controller;

import com.satya.SecApp.entity.Role;
import com.satya.SecApp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RoleService roleService;

    @GetMapping("/hi")
    public String welcomeAdmin(){
        return "hi i am admin";
    }

    @PostMapping
    public Role createRole(@RequestParam String name) {
        return roleService.createRole(name);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
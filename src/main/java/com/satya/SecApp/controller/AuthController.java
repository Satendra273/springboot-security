package com.satya.SecApp.controller;


import com.satya.SecApp.JwtTokenService;
import com.satya.SecApp.dto.LoginRequest;
import com.satya.SecApp.dto.RegisterRequest;
import com.satya.SecApp.entity.User;
import com.satya.SecApp.repo.UserRepository;

import com.satya.SecApp.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;


    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User register(
            @RequestBody RegisterRequest request) {

        return userService.register(request);
    }




    //jwt auth login

    @PostMapping("/login")
    public String  login (@RequestBody LoginRequest loginRequest){

        System.out.println("login api hit");
       Authentication authentication =
           authenticationManager
                   .authenticate(
                           new UsernamePasswordAuthenticationToken(
                                   loginRequest.getEmail()
                                   , loginRequest.getPassword())
                   );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String s = jwtTokenService.generateToken(user);

        System.out.println(jwtTokenService.getRoles(s));
        System.out.println(authentication.getName());

return s;

    }


}
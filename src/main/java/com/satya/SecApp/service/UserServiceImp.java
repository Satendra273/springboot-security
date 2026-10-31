package com.satya.SecApp.service;


import com.satya.SecApp.entity.User;
import com.satya.SecApp.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserDetailsService {


   private final UserRepository userRepository;



    public UserServiceImp(UserRepository userRepository ) {
        this.userRepository = userRepository;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user =  userRepository.findByEmail(username);
        User userIsFound = user.orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        return new MyUserDetails(userIsFound);

    }



}

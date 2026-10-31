package com.satya.SecApp.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


public class SecurityConfigByInMemory {


    @Bean
    public UserDetailsService users(){
        UserDetails user1 = User.builder().username("satya").password("1234").roles("USER").build();
        UserDetails user2 = User.builder().username("ram").password("123444").roles("USER","ADMIN").build();
        return new InMemoryUserDetailsManager(user1,user2);
    }

    	@Bean
	public PasswordEncoder passwordEncode()
	{
	return NoOpPasswordEncoder.getInstance();
	}

}

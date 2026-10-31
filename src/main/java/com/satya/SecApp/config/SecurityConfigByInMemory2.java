package com.satya.SecApp.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class SecurityConfigByInMemory2 {


    @Bean
    public UserDetailsService users(){
       InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails user1 = User.builder().username("satya").password("1234").roles("USER").build();
        manager.createUser(user1);
        return manager;

    }

    	@Bean
	public PasswordEncoder passwordEncode()
	{
	return NoOpPasswordEncoder.getInstance();
	}

}

package com.marketmaven.MarketMaven.Ecommerce.security;


import com.marketmaven.MarketMaven.Ecommerce.dto.UserDto;
import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import com.marketmaven.MarketMaven.Ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AuthUser.builder()//create an object for AuthUser if username found in the db.,so AuthUser is called here
                .user(user)
                .build();
    }
}
//Defines a custom user details service for spring security.
//loads the information from the database using user's email.if user exists ,it wraps the user entity in authuser for authentication.
//if user not found throws username not found exception,
//This service is used by springsecurity to authentication users during login.
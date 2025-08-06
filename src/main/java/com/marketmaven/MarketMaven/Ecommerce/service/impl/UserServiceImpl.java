package com.marketmaven.MarketMaven.Ecommerce.service.impl;

import com.marketmaven.MarketMaven.Ecommerce.dto.LoginRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.dto.UserDto;
import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import com.marketmaven.MarketMaven.Ecommerce.enums.UserRole;
import com.marketmaven.MarketMaven.Ecommerce.exception.InvalidCredentialException;
import com.marketmaven.MarketMaven.Ecommerce.exception.NotFoundException;
import com.marketmaven.MarketMaven.Ecommerce.mapper.EntityDtoMapper;
import com.marketmaven.MarketMaven.Ecommerce.repository.UserRepo;
import com.marketmaven.MarketMaven.Ecommerce.security.JwtUtils;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private  final EntityDtoMapper entityDtoMapper;

    @Override
    public Response registerUser(UserDto registrationRequest) {
        UserRole role=UserRole.USER;
        if(registrationRequest.getRole()!=null && registrationRequest.getRole().equalsIgnoreCase("admin")){
            role=UserRole.ADMIN;
        }

    User user=User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser=userRepo.save(user);
        UserDto userDto=entityDtoMapper.mapUserToDtoBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User Successfully Registered")
                .userData(userDto)
                .build();
    }

    //Login Implementation

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user=userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFoundException("Email Not Found"));
        //if email is already present
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Passoword does not match");
        }
        String token=jwtUtils.generateToken(user);

        return Response.builder()
                .status(200)
                .message("User Successfully login")
                .token(token)
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users=userRepo.findAll();
        List<UserDto> userDtos=users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();
        return Response.builder()
                .status(200)
                .message("Successful")
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        log.info("User Email is: "+email);
        return userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user=getLoggedInUser();
        UserDto userDto=entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);
        return Response.builder()
                .status(200)
                .userData(userDto)
                .build();
    }
}

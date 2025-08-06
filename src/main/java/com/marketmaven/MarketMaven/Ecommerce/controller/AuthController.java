package com.marketmaven.MarketMaven.Ecommerce.controller;

//Handles authentication and authroization
import com.marketmaven.MarketMaven.Ecommerce.dto.LoginRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.dto.UserDto;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")// All URLs in this controller will start with "/auth"
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;// Create link to UserService to use its functions

    @PostMapping("/register")// Handle POST requests to "/auth/register" for new user registration
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registrationRequest)// Method that takes user data and creates new account
    // Returns a response wrapped in HTTP format
    {
        return ResponseEntity.ok().body(userService.registerUser(registrationRequest));
        // Call service to register user and return success response
    }

    @PostMapping("/login")// Handle POST requests to "/auth/login" for user login
    public ResponseEntity<Response> logInUser(@RequestBody LoginRequest loginRequest)// Method that takes login details and checks if they're correct
    // Returns a response wrapped in HTTP format
    {
        return ResponseEntity.ok().body(userService.loginUser(loginRequest));
        // Call service to login user and return success response
    }
}
//2 main actions:User registration,user login.
//Accepts the data in JSON Format
//Performs the actions via userService
//Returns a response indicating success or failure.

//auth/register
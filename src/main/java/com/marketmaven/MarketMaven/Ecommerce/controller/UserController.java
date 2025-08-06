package com.marketmaven.MarketMaven.Ecommerce.controller;

//Handles user management operations

import com.marketmaven.MarketMaven.Ecommerce.dto.LoginRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.UserService;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")// All URLs in this controller will start with "/user"
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;// Create link to UserService to use its functions

    @GetMapping("/get-all")// Handle GET requests to fetch all users
    @PreAuthorize("hasAuthority('ADMIN')")//Only ADMIN can access it
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
        // Call service to get all users and return response
    }
    @GetMapping("/my-info")// Handle GET requests to get current user's info and orders
    public ResponseEntity<Response> getUserInfoAndOrderHistory() {
        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());
        // Call service to get user info and order history
    }
}
/*
This controller has two main functions:
    Get all users
    Get current user's information and order history
 */

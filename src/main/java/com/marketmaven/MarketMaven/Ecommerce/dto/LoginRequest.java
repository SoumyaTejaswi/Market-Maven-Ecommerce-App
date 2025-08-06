package com.marketmaven.MarketMaven.Ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class LoginRequest {
    @NotBlank(message = "email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;
}
//Contains login credentials (email and password) used for user authentication requests.
//Uses validation to ensure fields aren't blank.
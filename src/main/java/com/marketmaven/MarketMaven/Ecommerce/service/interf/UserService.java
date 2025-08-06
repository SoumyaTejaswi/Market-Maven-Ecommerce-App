package com.marketmaven.MarketMaven.Ecommerce.service.interf;


import com.marketmaven.MarketMaven.Ecommerce.dto.LoginRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.dto.UserDto;
import com.marketmaven.MarketMaven.Ecommerce.entity.User;

//Have methods to register a user
public interface UserService
{
    Response registerUser(UserDto registrationRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getLoggedInUser();

    Response getUserInfoAndOrderHistory();
}

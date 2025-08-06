package com.marketmaven.MarketMaven.Ecommerce.repository;

import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);//Search user by their email.Returns an optional to handles other endpoints where user might not exist
}
//Manager user account data
//Handles user authentication details
//db transactions management(save(),findById(),findall(),delete(),count()).
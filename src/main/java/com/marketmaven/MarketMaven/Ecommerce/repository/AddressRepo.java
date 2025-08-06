package com.marketmaven.MarketMaven.Ecommerce.repository;


import com.marketmaven.MarketMaven.Ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
//Manages user address-related db operation.
//Handles CRUD operations for shipping and billing addresses
//Maps address entities to db tables.
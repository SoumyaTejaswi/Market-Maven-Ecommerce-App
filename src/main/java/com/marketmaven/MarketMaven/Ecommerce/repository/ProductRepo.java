package com.marketmaven.MarketMaven.Ecommerce.repository;

import com.marketmaven.MarketMaven.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long categoryId);//search product by category id
    List<Product> findByNameOrDescriptionContaining(String name,String description);//search prod by name and description
    //Jpa automatics search product by category,name or description
}

//Handles CRUD operations
//Stores product details in the db

package com.marketmaven.MarketMaven.Ecommerce.repository;

import com.marketmaven.MarketMaven.Ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
//Handles product category management
//Stores and retreives categories
//Enable category based product filtering and organization

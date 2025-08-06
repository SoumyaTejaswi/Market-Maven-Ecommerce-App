package com.marketmaven.MarketMaven.Ecommerce.repository;

import com.marketmaven.MarketMaven.Ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepo  extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
}
/*
Handles Customer's order management
Stores order status dates and totals
Links product to specific orders
 */
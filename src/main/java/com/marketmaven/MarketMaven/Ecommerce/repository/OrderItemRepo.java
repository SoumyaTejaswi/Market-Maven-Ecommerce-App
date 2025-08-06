package com.marketmaven.MarketMaven.Ecommerce.repository;

import com.marketmaven.MarketMaven.Ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long>, JpaSpecificationExecutor<OrderItem> {//specification are used to create custom query in a more flexible way,helps to create search queries based on the given condition,helps to reduce in and helps to shorten your code,helps you to create reusable codes,for eg if i want to search the order item by user,product or order i can provide just one method using specification and search for the order item.

}
/*
manage INDIVIDUAL items within orders
Store product quantities,price per item
Links products to specific orders
*/

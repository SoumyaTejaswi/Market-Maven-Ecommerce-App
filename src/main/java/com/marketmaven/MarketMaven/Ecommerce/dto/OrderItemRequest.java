package com.marketmaven.MarketMaven.Ecommerce.dto;

import lombok.Data;

@Data

public class OrderItemRequest {
    private int productId;
    private int quantity;
}
//Used for creating/updating individual order items, containing the request parameters needed for order item operations.
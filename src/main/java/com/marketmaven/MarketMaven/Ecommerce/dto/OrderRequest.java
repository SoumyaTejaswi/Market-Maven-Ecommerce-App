package com.marketmaven.MarketMaven.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.marketmaven.MarketMaven.Ecommerce.entity.OrderItem;
import com.marketmaven.MarketMaven.Ecommerce.entity.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

    private BigDecimal totalPrice;
    private List<OrderItem> items;
    private Payment paymentInfo;
}
//Handles incoming order creation requests, containing necessary information to create a new order.
package com.marketmaven.MarketMaven.Ecommerce.dto;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private String status;
    private UserDto user;
    private ProductDto product;
    private LocalDateTime createdDate;
}
//Represents individual items within an order, likely containing product details, quantity, and price per item.

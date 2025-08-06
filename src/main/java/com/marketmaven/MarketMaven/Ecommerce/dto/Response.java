package com.marketmaven.MarketMaven.Ecommerce.dto;
//Why Response:A generic class for all our responses which is going to output all our responses.

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) //any properties which had null valiues is going to be ignored.
public class Response {
    private int status;//http status code for all our responses
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now(); //date and time in which such request was sent out.

    private String token;
    private String role;
    private String EXPIRATION_TIME;

    private int totalPage;
    private long totalElement;

    private AddressDto address;

    private UserDto userData;
    private List<UserDto> userList;

    private CategoryDto category;
    private List<CategoryDto> categoryList;

    private ProductDto product;
    private List<ProductDto> productList;

    private OrderItemDto orderItem;
    private List<OrderItemDto> orderItemList;

    private OrderDto order;
    private List<CategoryDto> orderList;
}
//Generic response object likely used to standardize API responses with status, messages, or data.
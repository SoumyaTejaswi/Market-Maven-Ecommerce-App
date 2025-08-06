package com.marketmaven.MarketMaven.Ecommerce.controller;
//Handles order item operations
import com.marketmaven.MarketMaven.Ecommerce.dto.OrderRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.enums.OrderStatus;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")// All URLs in this controller will start with "/order"
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;// Create link to OrderItemService to use its functions

    @PostMapping("/create")// Handle POST requests to create a new order
    public ResponseEntity<Response> placeOrder(@RequestBody OrderRequest orderRequest) {
        // Call service to create order and return response
        return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
    }
    @PutMapping("/update-item-status/{orderitemId}")// Handle PUT requests to update order status
    @PreAuthorize("hasAuthority('ADMIN')")// Only ADMIN can DO THAT
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable Long orderitemId,@RequestParam String status) {
        return ResponseEntity.ok(orderItemService.updateOrderItemStatus(orderitemId,status));
        // Call service to update status and return response
    }

    @GetMapping("/filter")// Handle GET requests to filter and search orders
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> filterOrderItems(
            // Optional date range filters
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            // Optional status and item filters
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long itemId,
            // Pagination parameters with defaults
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size) {
        // Set up pagination with sorting by id in descending order
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        // Convert status string to enum if provided
        OrderStatus orderStatus=status!=null?OrderStatus.valueOf(status.toUpperCase()):null;

        // Call service to filter orders and return response
        return ResponseEntity.ok(orderItemService.filterOrderItems(orderStatus, startDate, endDate, itemId, pageable));
    }
}
//This controller manages order operations with three main features:

    //Place new orders
    //Update order status
    //Filter and search orders with various criteria

//The filter endpoint is particularly powerful as it allows searching by:
    //Date range
    //Order status
    //Specific item
    //With pagination support
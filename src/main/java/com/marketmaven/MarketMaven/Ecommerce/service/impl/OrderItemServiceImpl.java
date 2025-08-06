package com.marketmaven.MarketMaven.Ecommerce.service.impl;

import com.marketmaven.MarketMaven.Ecommerce.dto.OrderItemDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.OrderItemRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.OrderRequest;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.entity.Order;
import com.marketmaven.MarketMaven.Ecommerce.entity.OrderItem;
import com.marketmaven.MarketMaven.Ecommerce.entity.Product;
import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import com.marketmaven.MarketMaven.Ecommerce.enums.OrderStatus;
import com.marketmaven.MarketMaven.Ecommerce.exception.NotFoundException;
import com.marketmaven.MarketMaven.Ecommerce.mapper.EntityDtoMapper;
import com.marketmaven.MarketMaven.Ecommerce.repository.OrderItemRepo;
import com.marketmaven.MarketMaven.Ecommerce.repository.OrderRepo;
import com.marketmaven.MarketMaven.Ecommerce.repository.ProductRepo;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.OrderItemService;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.UserService;
import com.marketmaven.MarketMaven.Ecommerce.specification.OrderItemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl  implements OrderItemService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;

    //implementation to place an order
    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        User user=userService.getLoggedInUser();
        //map order request to order entities
        List<OrderItem> orderItems=orderRequest.getItems().stream().map(orderItemRequest ->{
            Product product=productRepo.findById(orderItemRequest.getProduct().getId())
                    .orElseThrow(()->new NotFoundException("Product Not Found"));
            OrderItem orderItem=new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQty(orderItemRequest.getQty());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQty())));//set price according to the quantity
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;

        }).collect(Collectors.toList());
        //calculate the price of all the items
        BigDecimal totalPrice=orderRequest.getTotalPrice()!=null
                ? (orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO)>0
                ? orderRequest.getTotalPrice()
                :orderItems.stream().map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add))
                :orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add)
                ;

        //create order entity
        Order order=new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        //SET THE ORDER REFERENCE IN EACH ORDER ITEM
        orderItems.forEach(orderItem->orderItem.setOrder(order));

        orderRepo.save(order);

        return Response.builder()
                .status(200)
                .message("Order Placed Successfully")
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem=orderItemRepo.findById(orderItemId)
                .orElseThrow(()->new NotFoundException("Order Item Not Found"));
        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        return Response.builder()
                .status(200)
                .message("Order Item Updated Successfully")
                .build();
    }

    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec= (OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate,endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage=orderItemRepo.findAll(spec,pageable);

        if (orderItemPage.isEmpty()){
            throw new NotFoundException("Order Item Not Found");
        }
        List<OrderItemDto> orderItemDto=orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .orderItemList(orderItemDto)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();

    }
}


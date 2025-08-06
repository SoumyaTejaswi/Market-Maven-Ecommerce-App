package com.marketmaven.MarketMaven.Ecommerce.specification;

import com.marketmaven.MarketMaven.Ecommerce.entity.OrderItem;
import com.marketmaven.MarketMaven.Ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
//Static Specification methods for orderItem Queries

public class OrderItemSpecification {
    //return statement is going to check if there is a status on the query
    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return ((root, query, criteriaBuilder) -> //returns a spec that checks if an order item has given status
                status!=null ? criteriaBuilder.equal(root.get("status"), status) : null);//if status is not equal to null,get the status and uses criteriaBuilder to create the equality condition.

    }
    //return statement is going to check the order items between the start date and the end date.
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate){//take start date and end date as input
        return ((root, query, criteriaBuilder) ->{
            if (startDate!=null || endDate!=null){//if both dates present
                return criteriaBuilder.between(root.get("createdDate"), startDate, endDate);//returns specification for that range
            } else if (startDate!=null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), startDate);//if start date exists then uses greater tha or equal that start date
            } else if (endDate!=null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate);//if only end date is given then returns the specification using lessthanorequal to
            } else {
                return null;//none present then returns null
            }
        });
    }
    //Generate specification to filter order items bi item id
    public static Specification<OrderItem> hasItemId(Long id){//returns order items with that id
        return ((root, query, criteriaBuilder) ->
                id!=null ? criteriaBuilder.equal(root.get("id"), id) : null);//if id exists then returns the order item with that item else null.
    }
}

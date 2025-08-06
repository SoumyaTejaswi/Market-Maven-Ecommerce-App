package com.marketmaven.MarketMaven.Ecommerce.mapper;

import com.marketmaven.MarketMaven.Ecommerce.dto.*;
import com.marketmaven.MarketMaven.Ecommerce.entity.*;
import org.springframework.stereotype.Component;
import java.util.Collection;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {
    //Create a method to map a user Entity to user DTO

    public UserDto mapUserToDtoBasic(User user){//Converts basic user entity to userDto
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());//Maps userDto id to user entity id
        userDto.setPhoneNumber(user.getPhoneNumber());//Maps userDto phoneNumber to user entity phoneNumber
        userDto.setEmail(user.getEmail());//Maps userDto email to user entity email
        userDto.setRole(user.getRole().name());//Maps userDto role to user entity user role
        userDto.setName(user.getName());//Maps userDto name to user entity name
        return userDto;//Returns simplified user data transfer object

    }

    public AddressDto mapAddressToDtoBasic(Address address){//Converts address entity to addressDto
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZip(address.getZip());
        return addressDto;
    }

    public CategoryDto mapCategoryToDtoBasic(Category category){//Converts Category entity to CategoryDto
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public OrderItemDto mapOrderItemToDtoBasic(OrderItem orderItem){//Converts OrderItem entity to OrderItemDto
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQty());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        return orderItemDto;
    }
    //Product entity to product dto
    public ProductDto mapProductToDtoBasic(Product product){//Converts Product entity to ProductDto
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }
    //Method to mpa user to address dto.
    public UserDto mapUserToDtoPlusAddress(User user){//Extends basic user mapping to include address
        UserDto userDto = mapUserToDtoBasic(user);
        if(user.getAddress() != null){//if the user address exists in db
            AddressDto addressDto = mapAddressToDtoBasic(user.getAddress());//then it maps the address in the db to the address dto
            userDto.setAddress(addressDto);//updates the address of the user to userdto
        }
        return userDto;//returns the userdto with address updated
    }
    //Map Order Item Dto to Product Dto
    public OrderItemDto mapOrderItemToDtoPlusProduct(OrderItem orderItem){//extends orderitem mapping to include product details
        OrderItemDto orderItemDto = mapOrderItemToDtoBasic(orderItem);//calls orderitemdto
        if(orderItem.getProduct()!=null){//if order item includes that product
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());//then it maps the product entity to product dto to include it in the order item section.
            orderItemDto.setProduct(productDto);

        }
        return orderItemDto;//returns the orderitemdto which includes that product info
    }

    public OrderItemDto mapOrderItemToDtoPlusProductAndUser(OrderItem orderItem){//extends orderitem mapping to include product details and user details
        OrderItemDto orderItemDto = mapOrderItemToDtoPlusProduct(orderItem);//sets the order item dto to include product details
        if(orderItem.getUser()!=null){//if a user has that order item in his order
            UserDto userDto=mapUserToDtoPlusAddress(orderItem.getUser());//maps the user address and that order item to dto
            orderItemDto.setUser(userDto);//returns the orderitem with that user updated.
        }
        return orderItemDto;//returns that order item dto
    }
    //User to DTO with address and order items history
    public UserDto mapUserToDtoPlusAddressAndOrderHistory(User user){//extends the user mapping to include basic info,address,order history.
        UserDto userDto = mapUserToDtoPlusAddress(user);//created userdto with user info and address in it
        if(user.getOrderItemList()!=null && !user.getOrderItemList().isEmpty()){//if user has any orderitems in order hostory and the order item list is not empty
            userDto.setOrderItemList(user.getOrderItemList()//if above consition is satisfied then includes the orderitems in userDto
                    .stream()//for processing elements sequentially and convert orderitem to dto
                    .map(this::mapOrderItemToDtoPlusProduct)//Order items mapped to product to include product info in userDto
                    .collect(Collectors.toList()));//converts stream to list.
        }
        return userDto;//returns the userDto with following info:Address,order item history and product info
    }
}

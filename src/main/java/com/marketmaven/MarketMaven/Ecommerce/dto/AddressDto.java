package com.marketmaven.MarketMaven.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.marketmaven.MarketMaven.Ecommerce.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data//Generates getters and setter
@JsonInclude(JsonInclude.Include.NON_NULL) //controls  JSON Serialization
@JsonIgnoreProperties(ignoreUnknown = true)//Handles unknown JSON Properties
@AllArgsConstructor//Generates constructors
@NoArgsConstructor
public class AddressDto {
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    private UserDto user;
    private LocalDateTime createdAt;
}
//Transfers address-related data (like street, city, state) between layers of the application, typically used for shipping/billing addresses.
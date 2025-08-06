package com.marketmaven.MarketMaven.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //helps to generate our getters and setters
@JsonInclude(JsonInclude.Include.NON_NULL) //ignore properties of something i did not pass values of
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor //autogenerate our constructor
@NoArgsConstructor //in case there is no constructore,no construcotr is going to be called.
public class CategoryDto {
    private Long id;
    private  String name;
    private List<ProductDto> productList;
}
//Handles product category information transfer, likely containing category name and other category-specific details.
package com.marketmaven.MarketMaven.Ecommerce.service.interf;

import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService
{
    //Method to create a product
    Response createProduct(Long categoryId, MultipartFile file, String name, String description, BigDecimal price);

    //Method to update a product
    Response updateProduct(Long productId,Long categoryId, MultipartFile file, String name, String description, BigDecimal price);

    //Delete a product
    Response deleteProduct(Long productId);

    Response getProductById(Long productId);

    Response getAllProducts();

    Response getProductByCategory(Long categoryId);

    Response searchProduct(String searchValue);


}

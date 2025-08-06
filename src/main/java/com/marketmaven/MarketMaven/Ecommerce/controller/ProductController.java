package com.marketmaven.MarketMaven.Ecommerce.controller;
//Manages product-related operations
import com.marketmaven.MarketMaven.Ecommerce.dto.CategoryDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.exception.InvalidCredentialException;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")// All URLs in this controller will start with "/product"
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;// Create link to ProductService to use its functions

    @PostMapping("/create")// Handle POST requests to create a new product
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(
            @RequestParam Long categoryId,
            @RequestParam MultipartFile image,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price

    ) {
        // Check if all required fields are provided
        if (categoryId == null || image.isEmpty()|| name.isEmpty()||description.isEmpty()||price==null) {
            throw new InvalidCredentialException("All fields are required");//else throws exception
        }
        return ResponseEntity.ok(productService.createProduct(categoryId, image, name, description, price));
        //if present,Call service to create product and return response
    }

    @PutMapping("/update")// Handle PUT requests to update an existing product
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @RequestParam Long productId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price

    ) {
        return ResponseEntity.ok(productService.updateProduct(productId,categoryId, image, name, description, price));
        // Call service to update product and return response
    }

    @DeleteMapping("/delete/{productId}")// Handle DELETE requests to remove a product
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.deleteProduct(productId));
        // Call service to delete product and return response
    }

    @GetMapping("/get-by-product-id/{productId}")// Handle GET requests to fetch a specific product
    public ResponseEntity<Response> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.getProductById(productId));
        // Call service to get product by ID and return response
    }

    @GetMapping("/get-all")// Handle GET requests to fetch all products
    public ResponseEntity<Response> getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
        // Call service to get all products and return response
    }

    @GetMapping("/get-by-category-id/{categoryId}")// Handle GET requests to fetch products by category
    public ResponseEntity<Response> getProductByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(productService.getProductByCategory(categoryId));
        // Call service to get products by category and return response
    }

    @PostMapping("/search/{searchValue}")// Handle POST requests to search products
    public ResponseEntity<Response> searchForProduct(@PathVariable("searchValue") String searchValue) {
        return ResponseEntity.ok().body(productService.searchProduct(searchValue));
        // Call service to search products and return response
    }
}

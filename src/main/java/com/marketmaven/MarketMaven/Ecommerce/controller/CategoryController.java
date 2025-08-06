package com.marketmaven.MarketMaven.Ecommerce.controller;
//Manages Product categories
import com.marketmaven.MarketMaven.Ecommerce.dto.CategoryDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")//all urls will start with /category
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;//category service functions are used here.

    @PostMapping("/create")// Handle POST requests to create a new category
    @PreAuthorize("hasAuthority('ADMIN')")// Only users with ADMIN role can access this endpoint
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto) {//categorydto is called to get the all the inputs from the user.
        return ResponseEntity.ok().body(categoryService.createCategory(categoryDto));
        // Call service to create category and return response
    }
    @GetMapping("/get-all")// Handle GET requests to fetch all categories
    public ResponseEntity<Response> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
        //Calls service to return all the categories and return response.
    }
    @PutMapping("/update/{categoryId}")// Handle PUT requests to update an existing category
    @PreAuthorize("hasAuthority('ADMIN')")//Only ADMIN can do that
    public ResponseEntity<Response> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryId,categoryDto));
        // Call service to update category and return response
    }
    @DeleteMapping("/delete/{categoryId}")// Handle DELETE requests to remove a category
    @PreAuthorize("hasAuthority('ADMIN')")// Only ADMIN can delete categories
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.deleteeCategory(categoryId));
        // Call service to delete category and return response
    }
    @GetMapping("/get-category-by-id/{categoryId}")// Handle GET requests to fetch a specific category by ID
    public ResponseEntity<Response> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
        // Call service to get category by ID and return response
    }
}

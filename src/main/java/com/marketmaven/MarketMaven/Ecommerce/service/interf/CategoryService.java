package com.marketmaven.MarketMaven.Ecommerce.service.interf;

import com.marketmaven.MarketMaven.Ecommerce.dto.CategoryDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;

public interface CategoryService
{

    Response createCategory(CategoryDto categoryRequest);

    Response updateCategory(Long categoryId,CategoryDto categoryRequest);

    Response getAllCategories();

    Response getCategoryById(Long categoryId);

    Response deleteeCategory(Long categoryId);

}

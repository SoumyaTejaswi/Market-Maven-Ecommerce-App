package com.marketmaven.MarketMaven.Ecommerce.service.impl;

import com.marketmaven.MarketMaven.Ecommerce.dto.ProductDto;
import com.marketmaven.MarketMaven.Ecommerce.dto.Response;
import com.marketmaven.MarketMaven.Ecommerce.entity.Category;
import com.marketmaven.MarketMaven.Ecommerce.entity.Product;
import com.marketmaven.MarketMaven.Ecommerce.exception.NotFoundException;
import com.marketmaven.MarketMaven.Ecommerce.mapper.EntityDtoMapper;
import com.marketmaven.MarketMaven.Ecommerce.repository.CategoryRepo;
import com.marketmaven.MarketMaven.Ecommerce.repository.ProductRepo;
import com.marketmaven.MarketMaven.Ecommerce.service.AwsS3Service;
import com.marketmaven.MarketMaven.Ecommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;

    @Override
    public Response createProduct(Long categoryId, MultipartFile file, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException("Category not found"));
        String  productImageUrl=awsS3Service.saveImageToS3(file);

        //product entity
        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product has been created successfully")
                .build();
    }

    @Override
    //Product id is required all are optional
    public Response updateProduct(Long productId, Long categoryId, MultipartFile file, String name, String description, BigDecimal price) {
        //Checking if product exists or not
        Product product=productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found"));

        //Update the productname or it should remain in the samecategory
        Category category=null;
        String  productImageUrl=null;
        if (categoryId != null){
            category=categoryRepo.findById(productId).orElseThrow(()->new NotFoundException("Category not found"));
        }
        if(file!=null && !file.isEmpty()){
            productImageUrl=awsS3Service.saveImageToS3(file);
        }
        if (category!=null) product.setCategory(category);
        if (name!=null) product.setName(name);
        if (price!=null) product.setPrice(price);
        if (description!=null) product.setDescription(description);
        if (productImageUrl!=null) product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product has been updated successfully")
                .build();

    }

    @Override
    public Response deleteProduct(Long productId) {
        //Check if the product is there
        Product product=productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found"));
        productRepo.delete(product);
        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product=productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found"));
        ProductDto productDto=entityDtoMapper.mapProductToDtoBasic(product);
        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productList=productRepo.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public Response getProductByCategory(Long categoryId) {
        List<Product> products=productRepo.findByCategoryId(categoryId);
        if(products.isEmpty()){
            throw new NotFoundException("Product not found for this Category");
        }
        List<ProductDto> productDtoList=products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();

    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> product=productRepo.findByNameOrDescriptionContaining(searchValue,searchValue);

        if (product.isEmpty()){
            throw new NotFoundException("No Products found");
        }
        List<ProductDto> productDtoList=product.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();


    }
}

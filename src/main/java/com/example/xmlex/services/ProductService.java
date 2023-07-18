package com.example.xmlex.services;

import com.example.xmlex.models.dtos.ProductSeedDto;

import java.util.List;

public interface ProductService {
    long getProductsCount();

    void seedProducts(List<ProductSeedDto> products);
}

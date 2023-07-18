package com.example.xmlex.services;

import com.example.xmlex.models.dtos.ProductSeedDto;
import com.example.xmlex.models.dtos.ProductViewRootDto;

import java.util.List;

public interface ProductService {
    long getProductsCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findProductInRangeWithNoBuyer();
}
